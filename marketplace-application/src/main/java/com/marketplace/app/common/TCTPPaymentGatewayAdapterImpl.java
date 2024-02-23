package com.marketplace.app.common;

import java.net.URI;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.payment.PaymentResult;
import com.marketplace.domain.payment.PaymentTokenRequest;
import com.marketplace.domain.payment.PaymentTokenResponse;
import com.marketplace.domain.payment.TCTPPaymentGatewayAdapter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class TCTPPaymentGatewayAdapterImpl implements TCTPPaymentGatewayAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(TCTPPaymentGatewayAdapter.class);
	
	@Value("${app.payment.merchant-sha-key}") 
	private String merchantShaKey;
	
	@Value("${app.payment.merchant-id}")
	private String merchantId;
	
	@Value("${app.payment.token-request-url}")
	private String tokenRequestUrl;
	
	@Value("${app.misc.website-url}")
	private String consumerUrl;
	
	@Autowired
	private RestClient restClient;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private SecretKey key;
	
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(merchantShaKey.getBytes());
	}

//	@Retryable
	@Override
	public PaymentTokenResponse requestPaymentToken(PaymentTokenRequest input) {
		try {
			var claims = new HashMap<String, Object>();
			claims.put("merchantID", merchantId);
			claims.put("invoiceNo", input.getInvoiceNo());
			claims.put("description", input.getDescription());
			claims.put("amount", input.getAmount().doubleValue());
			claims.put("currencyCode", "MMK"); 
			
			var frontendReturnUrl = String.format("%s/profile/shops/%d/payment", consumerUrl, input.getShopId());
			claims.put("frontendReturnUrl", frontendReturnUrl);
			//claims.put("paymentChannel", new String[] {"MPU", "WEBPAY", "EWALLET", "QRC", "IMBANK"});

			var encoded = Jwts.builder()
					.claims()
					.add(claims)
					.and()
					.signWith(key, Jwts.SIG.HS256)
					.compact();
			
			var body = new HashMap<String, Object>();
			body.put("payload", encoded);
			
			var response = restClient.post()
					.uri(new URI(tokenRequestUrl))
					.contentType(MediaType.APPLICATION_JSON)
					.body(objectMapper.writeValueAsString(body))
					.retrieve()
					.toEntity(String.class);
			
			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new ApplicationException("Payment token request failed");
			}
			
			var json = objectMapper.readTree(response.getBody());
			var payload = json.get("payload").textValue();
			
			var decoded = Jwts.parser()
	            .verifyWith(key)
	            .build()
	            .parseSignedClaims(payload)
	            .getPayload();
			
			var result = new PaymentTokenResponse();
			result.setWebPaymentUrl(decoded.get("webPaymentUrl", String.class));
			result.setPaymentToken(decoded.get("paymentToken", String.class));
			result.setRespCode(decoded.get("respCode", String.class));
			result.setRespDesc(decoded.get("respDesc", String.class));
			
			return result;
		} catch (Exception e) {
			log.error("Payment token request error: {}", e.getMessage());
			throw new ApplicationException("Payment token request failed");
		}
	}

	@Override
	public PaymentResult decodeResultPayload(String payload) {
		try {
			var decoded = Jwts.parser()
					.verifyWith(key)
		            .build()
		            .parseSignedClaims(payload)
		            .getPayload();
			
			var result = new PaymentResult();
			result.setMerchantId(decoded.get("merchantID", String.class));
			result.setInvoiceNo(decoded.get("invoiceNo", String.class));
			result.setCardNo(decoded.get("cardNo", String.class));
			result.setAmount(decoded.get("amount", Double.class));
			result.setCurrencyCode(decoded.get("currencyCode", String.class));
			result.setTranRef(decoded.get("tranRef", String.class));
			result.setReferenceNo(decoded.get("referenceNo", String.class));
			result.setAgentCode(decoded.get("agentCode", String.class));
			result.setChannelCode(decoded.get("channelCode", String.class));
			result.setApprovalCode(decoded.get("approvalCode", String.class));
			result.setEci(decoded.get("eci", String.class));
			result.setTransactionDateTime(decoded.get("transactionDateTime", String.class));
			result.setRespCode(decoded.get("respCode", String.class));
			result.setRespDesc(decoded.get("respDesc", String.class));
			return result;
		} catch (Exception e) {
			log.error("Failed to decode payment result: {}", e.getMessage());
		}
		return null;
	}

}
