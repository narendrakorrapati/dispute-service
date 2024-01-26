package com.narendra.service;

import java.util.List;

import com.narendra.dto.DisputeSearchResponse;

public interface DisputeSearchService {

	List<DisputeSearchResponse> findAllSettledTransactions();

}
