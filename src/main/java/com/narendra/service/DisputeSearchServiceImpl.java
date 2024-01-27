package com.narendra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.narendra.dto.DisputeSearchResponse;

@Service
public class DisputeSearchServiceImpl implements DisputeSearchService{

	@Autowired
	private WebClient webClient;
	
	@Autowired
	private SettlementServiceEndpoint settlementEndpoint;
	
	@Override
	public List<DisputeSearchResponse> findAllSettledTransactions() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the principal (authenticated user)
        Object principal = authentication.getPrincipal();
		
        
        if(principal instanceof Jwt) {
        	Jwt jwt = (Jwt) principal;
        	String authToken = "Bearer " + jwt.getTokenValue();
        	
        	return webClient.get().uri(settlementEndpoint.getEndPoint() + settlementEndpoint.getSettlTransaction())
    				.headers(t -> t.add("Authorization", authToken)).retrieve().bodyToFlux(DisputeSearchResponse.class)
    				.collectList().block();
        }
		throw new RuntimeException("Can't make request to settlement-service without authorization");
		
	}
}
