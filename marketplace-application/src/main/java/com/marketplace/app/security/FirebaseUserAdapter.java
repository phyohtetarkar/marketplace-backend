package com.marketplace.app.security;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.domain.ApplicationException;

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
	
	public AuthUser getUserData(String idToken) {
		var params = new HashMap<String, Object>();
		params.put("key", apiKey);

		try {
			var body = new HashMap<String, Object>();
			body.put("idToken", idToken);

			var response = restClient.post()
					.uri(GET_USER_DATA_URL, apiKey)
					.contentType(MediaType.APPLICATION_JSON)
					.body(body)
					.retrieve()
					.onStatus(new FirebaseAuthApiErrorHandler(objectMapper))
					.toEntity(String.class);

			var json = objectMapper.readTree(response.getBody());
			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new ApplicationException(json.get("error").get("message").asText());
			}
			
			var array = json.get("users").elements();
			
			if (!array.hasNext()) {
				throw new AccessDeniedException("User not found");
			}
			
			var root = array.next();
			
			var result = new AuthUser();
			result.setUid(root.get("localId").asText());
			result.setName(root.get("displayName").asText());
			result.setEmail(root.get("email").asText(null));
			result.setImageUrl(root.get("photoUrl").asText(null));
			return result;
		} catch (Exception e) {
			log.error("Fetch user error: {}", e.getMessage());
			throw new InvalidBearerTokenException(e.getMessage());
		}
	}

}
