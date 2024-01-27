package com.narendra.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class UserController {

	@GetMapping("/token")
	public Object getAuthToken(@AuthenticationPrincipal UserDetails userDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the principal (authenticated user)
        Object principal = authentication.getPrincipal();

        // Get the authorities (roles)
        // Note: authorities are typically instances of GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

        // Access other details from the Authentication object as needed
        // For example, you can get the name, details, credentials, etc.

        // Print or use the retrieved information as needed
        System.out.println("Principal: " + principal);
        System.out.println("Authorities: " + authorities);

		return principal;
	}
	
	@GetMapping("/users")
	public String getUsers() {
		return "Working";
	}
	
	@DeleteMapping(path = "/users/{id}")
	//@Secured(value = {"ROLE_developer"})
	@PreAuthorize(value = "hasAuthority('ROLE_user') or #id == #jwt.subject")
	public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		return "Deleted user with id " + id + " and Jwt sugject " + jwt.getSubject();
	}
	
	@GetMapping(path = "/users/{id}")
	@PostAuthorize("returnObject.userId == #jwt.subject")
	public UserRest getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		return new UserRest("narendra", "korrapati", "c2c5241f-510f-42aa-a8d0-eb846bf718e1");
	}
	
}
