package com.shoppingcenter.app.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shoppingcenter.app.common.AppProperties;
import com.shoppingcenter.domain.ApplicationException;
import com.shoppingcenter.domain.payment.PaymentGatewayAdapter;
import com.shoppingcenter.domain.payment.PaymentTokenRequest;
import com.shoppingcenter.domain.payment.PaymentTokenResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class PaymentGatewayAdapterImpl implements PaymentGatewayAdapter {

	@Autowired
	private AppProperties properties;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public PaymentTokenResponse requestPaymentToken(PaymentTokenRequest request) {
		try {
			var shaKey = properties.getMerchantShaKey();
			var key = Keys.hmacShaKeyFor(shaKey.getBytes());
			
			Map<String, Object> claims = new HashMap<>();
			claims.put("merchantID", properties.getMerchantId());
			claims.put("invoiceNo", request.getInvoiceNo());
			claims.put("description", request.getDescription());
			claims.put("amount", request.getAmount().doubleValue());
			claims.put("currencyCode", "MMK"); 
			

			var encoded = Jwts.builder().addClaims(claims)
					.signWith(key, SignatureAlgorithm.HS256)
					//.setHeader(Map.of("alg", "HS256", "type", "JWT"))
					.compact();
			
			//System.out.println(encoded);
			
			var body = new JSONObject();
			body.put("payload", encoded);
			
			var headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
			
			var entity = new HttpEntity<>(body.toString(), headers);
			
			var url = properties.getPaymentTokenRequestUrl();
			
			var response = restTemplate.postForEntity(url, entity, String.class);
			
			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new ApplicationException(response.getBody());
			}
			
			//System.out.println(response.getBody());
			
			var json = new JSONObject(response.getBody());
			var payload = json.getString("payload");
			
			var decoded = Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(payload)
	            .getBody();
			
			var result = new PaymentTokenResponse();
			result.setWebPaymentUrl(decoded.get("webPaymentUrl", String.class));
			result.setPaymentToken(decoded.get("paymentToken", String.class));
			result.setRespCode(decoded.get("respCode", String.class));
			result.setRespDesc(decoded.get("respDesc", String.class));
			
			System.out.println(result.toString());
			
			return result;
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
		
	}

}
