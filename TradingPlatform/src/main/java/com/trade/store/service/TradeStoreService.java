package com.trade.store.service;

import java.util.List;


import com.trade.store.binding.TradeStoreRequest;
import com.trade.store.binding.TradeStoreResponse;
import com.trade.store.exception.TradeStoeException;


public interface TradeStoreService {
	public List<TradeStoreResponse> getAllTradeRecord();
	public String saveTradeDetails(TradeStoreRequest storeRequest) throws TradeStoeException;
}
