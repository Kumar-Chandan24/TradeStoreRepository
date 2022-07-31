package com.trade.store.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.trade.store.entity.TradeStore;

@DataJpaTest
public class TradeStoreRepositoryTest {

	@Autowired
	private TradeStoreRepository repository;
	
	@BeforeEach
	public void startUp() {
		List<TradeStore> tradeStores = new ArrayList<>();
		tradeStores.add(new TradeStore("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N'));

		tradeStores.add(new TradeStore("T1", 2, "CP-2", "B2", LocalDate.now(), LocalDate.parse("2022-03-25"), 'N'));

		tradeStores.add(new TradeStore("T2", 3, "CP-1", "B2", LocalDate.now(), LocalDate.parse("2022-02-10"), 'N'));

		tradeStores.add(new TradeStore("T3", 4, "CP-2", "B3", LocalDate.now(), LocalDate.parse("2022-01-30"), 'N'));
		tradeStores.add(new TradeStore("T3", 4, "CP-2", "B3", LocalDate.parse("2022-02-20"), LocalDate.parse("2022-01-30"), 'N'));
		
		
		repository.saveAll(tradeStores);
	}
	
	@Test
	public void testForSaveTradeDetails() {
		TradeStore tradeStore = new TradeStore("T1", 3, "CP-2", "B2", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N');
		
		TradeStore  ts=repository.save(tradeStore);
		
		
		
		assertNotEquals(0, ts.getTradeNo());
	}
	

	@Test
	public void testForGetTradeDetailsByTradeId() {
		
		List<TradeStore> stores=repository.findByTradeId("T1");
		
		String actual=stores.stream().findAny().get().getTradeId();
		assertNotEquals(0, stores.size());
		assertEquals("T1", actual);
	}
	
	@Test
	public void testFindTradeDetailsByComparingMaturityDateWithTodayDate() {
		
		List<TradeStore> stores=repository.findTradeDetailsByComparingMaturityDateWithTodayDate(LocalDate.now());
		LocalDate expected = LocalDate.parse("2022-02-20");
		LocalDate actual = stores.stream().findAny().get().getMaturityDate();
		
		assertNotEquals(0, stores.size());
		assertEquals(expected, actual);
	}
}
