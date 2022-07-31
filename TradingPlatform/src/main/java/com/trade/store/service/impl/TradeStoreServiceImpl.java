package com.trade.store.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.store.binding.TradeStoreRequest;
import com.trade.store.binding.TradeStoreResponse;
import com.trade.store.entity.TradeStore;
import com.trade.store.exception.TradeStoeException;
import com.trade.store.repository.TradeStoreRepository;
import com.trade.store.service.TradeStoreService;

@Service
public class TradeStoreServiceImpl implements TradeStoreService {

	@Autowired
	private TradeStoreRepository repository;

	@Override
	public List<TradeStoreResponse> getAllTradeRecord() {
		List<TradeStoreResponse> tradereq = new ArrayList<>();
		TradeStoreResponse tsr = null;
		List<TradeStore> trs=repository.findAll();
		for (TradeStore tr : trs) {
			tsr = new TradeStoreResponse();
			BeanUtils.copyProperties(tr, tsr);
			tradereq.add(tsr);
		}
		return tradereq;
	}

	@SuppressWarnings("unused")
	@Override
	public String saveTradeDetails(TradeStoreRequest storeRequest) throws TradeStoeException {
		List<TradeStore> tdStores = repository.findByTradeId(storeRequest.getTradeId());

		if (0 < (LocalDate.now().compareTo(storeRequest.getMaturityDate()))) {
			return "Trade Details Not Allow for storing Reason: Maturity Date :" + storeRequest.getMaturityDate()
					+ " Less than Today: " + LocalDate.now();
		}
		if (tdStores.size() == 0) {
			TradeStore tradeStore = new TradeStore();
			BeanUtils.copyProperties(storeRequest, tradeStore);
			repository.save(tradeStore);
			return "Trade Details inserted into Trade Store";
		} else {
			String status = null;
			try {
				status = checkTradeStoreVersion(storeRequest, tdStores);
				return status;
			} catch (TradeStoeException e) {
				throw new TradeStoeException(e.getMessage());
			}
		}
	}

	private String checkTradeStoreVersion(TradeStoreRequest storeRequest, List<TradeStore> tradeStores)
			throws TradeStoeException {

		int maxVersion = tradeStores.stream().max(Comparator.comparingInt(TradeStore::getVersion)).get().getVersion();
		if (maxVersion > storeRequest.getVersion()) {
			throw new TradeStoeException("Not Acceptable : This Trade Version :" + storeRequest.getVersion()
					+ " is Lower than last existance Trade");
		} else if (maxVersion == storeRequest.getVersion()) {
			TradeStore trs = tradeStores.stream().filter(t -> storeRequest.getVersion() == t.getVersion()).findAny()
					.get();
			BeanUtils.copyProperties(storeRequest, trs);
			repository.save(trs);
			return "Trade Details Override Because This trade version :" + storeRequest.getVersion()
					+ " is already existed.";
		} else {
			TradeStore tradeStore = new TradeStore();
			BeanUtils.copyProperties(storeRequest, tradeStore);
			repository.save(tradeStore);
			return "Latest Version : " + storeRequest.getVersion() + " of Trade " + storeRequest.getTradeId()
					+ " Details Store ";
		}

	}

}
