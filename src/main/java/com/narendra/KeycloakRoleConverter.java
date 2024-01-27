package com.narendra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/*
 * Only the scope is converted to GrantedAuthority by default. 
 * We need to write this custom converter to convert roles also to GrantedAuthorities.
 * But the below implementation gets only the roles doesn't consider scopes.
 * We need to get claims for scope and set it here.
 */
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>>{

	@Override
	public Collection<GrantedAuthority> convert(Jwt source) {
		
		Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
		
		if(realmAccess == null && realmAccess.isEmpty())
			return new ArrayList<>();
		
		List<String> roles = (List<String>)realmAccess.get("roles");
		
		return roles.stream().map(roleName -> "ROLE_" + roleName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		
	}

}
