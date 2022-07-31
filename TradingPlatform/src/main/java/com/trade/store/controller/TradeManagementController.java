package com.trade.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trade.store.binding.TradeStoreRequest;
import com.trade.store.binding.TradeStoreResponse;
import com.trade.store.exception.TradeStoeException;
import com.trade.store.service.TradeStoreService;

@RestController
public class TradeManagementController {

	@Autowired
	private TradeStoreService storeService;

	@GetMapping(path = "/getAllTrades", produces = "application/json")
	public ResponseEntity<?> getAllTradeRecord() {
		List<TradeStoreResponse> tradeStoreRequests = storeService.getAllTradeRecord();
		return new ResponseEntity<List<TradeStoreResponse>>(tradeStoreRequests, HttpStatus.OK);
	}

	@PostMapping(path = "/saveTrade", consumes = "application/json")
	public ResponseEntity<?> saveTradeRecord(@RequestBody TradeStoreRequest storeRequest) throws TradeStoeException {

		String status = storeService.saveTradeDetails(storeRequest);

		return new ResponseEntity<String>(status, HttpStatus.CREATED);
	}

}
