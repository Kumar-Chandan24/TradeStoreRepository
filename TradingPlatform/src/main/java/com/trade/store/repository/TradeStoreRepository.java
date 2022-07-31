package com.trade.store.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trade.store.entity.TradeStore;

public interface TradeStoreRepository  extends JpaRepository<TradeStore, Long>{
	 List<TradeStore> findByTradeId(String tradeId);
	 
	 @Query(value = "select t from TradeStore t where t.maturityDate < :today")
	 List<TradeStore> findTradeDetailsByComparingMaturityDateWithTodayDate(@Param(value = "today") LocalDate today);
}
