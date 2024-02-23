package com.marketplace.app.security;

import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FirebaseUserAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(FirebaseUserAdapter.class);

	private static final String GET_USER_DATA_URL = "https://identitytoolkit.googleapis.com/v1/accounts:lookup?key={apiKey}";

	@Value("${app.firebase.api-key}")
	private String apiKey;
	
	@Autowired
	private RestClient restClient;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Retryable(noRetryFor = { AccessDeniedException.class })
	public AuthUser getUserData(String idToken) {
		var params = new HashMap<String, Object>();
		params.put("key", apiKey);

		try {
			var body = new HashMap<String, Object>();
			body.put("idToken", idToken);

			var response = restClient.post()
					.uri(GET_USER_DATA_URL, apiKey)
					.contentType(MediaType.APPLICATION_JSON)
					.body(objectMapper.writeValueAsString(body))
					.retrieve()
					.onStatus(new FirebaseAuthApiErrorHandler(objectMapper))
					.toEntity(String.class);

			var json = objectMapper.readTree(response.getBody());
//			if (!response.getStatusCode().is2xxSuccessful()) {
//				throw new ApplicationException(json.get("error").get("message").asText());
//			}
			
			var array = json.get("users").elements();
			
			if (!array.hasNext()) {
				throw new AccessDeniedException("User not found");
			}
			
			var root = array.next();
			
			var result = new AuthUser();
			result.setUid(root.get("localId").textValue());
			result.setName(Optional.ofNullable(root.get("displayName")).map(JsonNode::textValue).orElse(null));
			result.setEmail(Optional.ofNullable(root.get("email")).map(JsonNode::textValue).orElse(null));
			result.setImageUrl(Optional.ofNullable(root.get("photoUrl")).map(JsonNode::textValue).orElse(null));
			return result;
		} catch (JsonProcessingException e) {
			log.error("Fetch user error: {}", e.getMessage());
			return null;
		} catch (Exception e) {
			log.error("Fetch user error: {}", e.getMessage());
			throw e;
		}
	}

}
