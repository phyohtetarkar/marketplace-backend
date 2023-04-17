package com.shoppingcenter.app.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

		// ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
		// ErrorCode.AUTH_UNAUTHORIZED);
		// error.setMessage("Unauthorized!");
		// PrintWriter out = response.getWriter();
		// response.setContentType("application/json");
		// response.setCharacterEncoding("UTF-8");
		// response.setStatus(error.getStatus());
		// out.print(new ObjectMapper().writeValueAsString(error));
		// out.flush();
	}

}
