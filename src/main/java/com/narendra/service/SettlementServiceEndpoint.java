package com.narendra.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementServiceEndpoint {

	@Value("${settlement.service.endpoint}")
	private String endPoint;
	@Value("${settlement.service.settl-transaction}")
	private String settlTransaction;
}
