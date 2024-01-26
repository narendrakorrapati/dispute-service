package com.narendra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		return webClient.get()
		.uri(settlementEndpoint.getEndPoint() + settlementEndpoint.getSettlTransaction())
		.retrieve()
		.bodyToFlux(DisputeSearchResponse.class)
		.collectList()
		.block();
	}
}
