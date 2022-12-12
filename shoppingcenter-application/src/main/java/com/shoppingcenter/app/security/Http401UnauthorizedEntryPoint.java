package com.shoppingcenter.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
		
//		ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ErrorCode.AUTH_UNAUTHORIZED);
//		error.setMessage("Unauthorized!");
//		PrintWriter out = response.getWriter();
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
//		response.setStatus(error.getStatus());
//		out.print(new ObjectMapper().writeValueAsString(error));
//		out.flush();
	}

}
