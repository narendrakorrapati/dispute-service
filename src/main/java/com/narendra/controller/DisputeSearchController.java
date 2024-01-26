package com.narendra.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.narendra.dto.DisputeSearchResponse;
import com.narendra.service.DisputeSearchService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/api/v1/dispute-search")
public class DisputeSearchController {
	
	@Autowired
	private DisputeSearchService disputeSearchService;
	
	@GetMapping("/")
	@CircuitBreaker(name = "disputeService", fallbackMethod = "getForSearchFallback")
	public ResponseEntity<List<DisputeSearchResponse>> getForSearch() {
		return new ResponseEntity<List<DisputeSearchResponse>>(disputeSearchService.findAllSettledTransactions(), HttpStatus.OK);
	}
	
	public ResponseEntity<List<DisputeSearchResponse>> getForSearchFallback(Exception e) {
		
		List<DisputeSearchResponse> fallbackList = List.of(DisputeSearchResponse.builder()
				.idCol(100L)
				.acquirer("TST")
				.issuer("TST")
				.amount(100.0)
				.messageType("210")
				.responseCode("0")
				.dateTime(LocalDateTime.now())
				.build());
		
		return new ResponseEntity<List<DisputeSearchResponse>>(fallbackList, HttpStatus.OK);
	}
}
