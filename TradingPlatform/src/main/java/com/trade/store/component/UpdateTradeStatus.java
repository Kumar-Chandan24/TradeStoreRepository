package com.trade.store.component;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.trade.store.entity.TradeStore;
import com.trade.store.repository.TradeStoreRepository;

@Service
public class UpdateTradeStatus {

	@Autowired
	private TradeStoreRepository storeRepository;
	
	@Scheduled(cron = "${corn.expression.value}")
	public void updateTradeStatusByMaturityDate() {
		System.out.println(LocalDateTime.now());
		
		List<TradeStore> tradeStores=storeRepository.findTradeDetailsByComparingMaturityDateWithTodayDate(LocalDate.now());
		List<TradeStore> stores=new ArrayList<>();
		for(TradeStore store:tradeStores) {
			store.setIsExpired('Y');
			stores.add(store);
		}
		if(stores.size()>0) {
			storeRepository.saveAll(stores);
		}
		
	}
}
