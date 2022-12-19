package net.codejava.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.codejava.model.User;
import net.codejava.jwt.JwtTokenUtil;
import net.codejava.request.AuthRequest;
import net.codejava.response.AuthResponse;
import net.codejava.response.StatusResp;

@RestController
@RequestMapping("/api")
public class AuthApi extends AbstractRestController {
	@Autowired
	AuthenticationManager authManager;
	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("/auth/login")
	public StatusResp login(@RequestBody @Valid AuthRequest request ,  BindingResult bindingResult) throws Exception {
		checkBindingResult(bindingResult);
		StatusResp statusResp = new StatusResp();
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = (User) authentication.getPrincipal();
		String accessToken = jwtUtil.generateAccessToken(user);
		AuthResponse response = new AuthResponse(user.getEmail(), accessToken, user.getRole().toString());
		statusResp.setData(response);
		return statusResp;
	}
}
