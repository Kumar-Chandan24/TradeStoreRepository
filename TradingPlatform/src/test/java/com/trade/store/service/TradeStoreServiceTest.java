package com.trade.store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.trade.store.binding.TradeStoreRequest;
import com.trade.store.entity.TradeStore;
import com.trade.store.exception.TradeStoeException;
import com.trade.store.repository.TradeStoreRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TradeStoreServiceTest {  
	@MockBean
	private TradeStoreRepository repository;

	@Autowired
	private TradeStoreService tradeStoreService;

	@Test
	public void testForGetAllTradeRecords() {
		List<TradeStore> tradeStores = new ArrayList<>();
		tradeStores.add(new TradeStore("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N'));

		tradeStores.add(new TradeStore("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-03-25"), 'N'));

		tradeStores.add(new TradeStore("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-02-10"), 'N'));

		tradeStores.add(new TradeStore("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-01-30"), 'N'));

		when(repository.findAll()).thenReturn(tradeStores);

		int expected = 4;

		assertEquals(expected, tradeStoreService.getAllTradeRecord().size());
		assertNotEquals(5, tradeStoreService.getAllTradeRecord().size());

	}
	
	@Test
	public void testForSaveValidTradeDetails() throws TradeStoeException {
		TradeStore tradeStore = new TradeStore("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N');
		TradeStoreRequest request=new TradeStoreRequest("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N');
		
		
		
		when(repository.save(tradeStore)).thenReturn(tradeStore);
		when(repository.findByTradeId(request.getTradeId())).thenReturn(new ArrayList<>());
		
		String expected="Trade Details inserted into Trade Store";
		
		assertEquals(expected, tradeStoreService.saveTradeDetails(request));
	
	}
	
	
	@Test
	public void testForSaveInvalidTradeDetailsOfMaturityDateLessThanToday() throws TradeStoeException {
		
		TradeStoreRequest request=new TradeStoreRequest("T1", 1, "CP-1", "B1", LocalDate.parse("2022-07-29"), LocalDate.parse("2022-04-25"), 'N');
		
		
		String expected="Trade Details Not Allow for storing Reason: Maturity Date :" + request.getMaturityDate()
		+ " Less than Today: " + LocalDate.now();
		
		assertEquals(expected, tradeStoreService.saveTradeDetails(request));
	
	}
	
	@Test
	public void testForSaveTradeDetailsCheckVersionIsGreaterThanLastExixtedTradeVersion() throws TradeStoeException {
		TradeStore tradeStore = new TradeStore("T1", 5, "CP-2", "B2", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N');
		TradeStoreRequest request=new TradeStoreRequest("T1", 5, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N');
		
		List<TradeStore> tradeStores = new ArrayList<>();
		tradeStores.add(new TradeStore("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N'));

		tradeStores.add(new TradeStore("T1", 2, "CP-2", "B1", LocalDate.now(), LocalDate.parse("2022-03-25"), 'N'));

		tradeStores.add(new TradeStore("T1", 3, "CP-1", "B2", LocalDate.now(), LocalDate.parse("2022-02-10"), 'N'));

		tradeStores.add(new TradeStore("T1", 4, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-01-30"), 'N'));

		
		
		when(repository.save(tradeStore)).thenReturn(tradeStore);
		when(repository.findByTradeId(request.getTradeId())).thenReturn(tradeStores);
		
		String expected="Latest Version : " + request.getVersion() + " of Trade " + request.getTradeId()
		+ " Details Store ";
		
		assertEquals(expected, tradeStoreService.saveTradeDetails(request));
	
	}
	
	@Test
	public void testForSaveTradeDetailsCheckVersionIsEqualAndLastExixtedTradeVersionAndOverrideValue() throws TradeStoeException {
		TradeStore tradeStore = new TradeStore("T1", 4, "CP-2", "B2", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N');
		TradeStoreRequest request=new TradeStoreRequest("T1", 4, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N');
		
		List<TradeStore> tradeStores = new ArrayList<>();
		tradeStores.add(new TradeStore("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N'));

		tradeStores.add(new TradeStore("T1", 2, "CP-2", "B1", LocalDate.now(), LocalDate.parse("2022-03-25"), 'N'));

		tradeStores.add(new TradeStore("T1", 3, "CP-1", "B2", LocalDate.now(), LocalDate.parse("2022-02-10"), 'N'));

		tradeStores.add(new TradeStore("T1", 4, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-01-30"), 'N'));

		
		
		
		when(repository.save(tradeStore)).thenReturn(tradeStore);
		when(repository.findByTradeId(request.getTradeId())).thenReturn(tradeStores);
		
		String expected="Trade Details Override Because This trade version :" + request.getVersion()
		+ " is already existed.";
		
		assertEquals(expected, tradeStoreService.saveTradeDetails(request));
	
	}
	
	@Test
	public void testForSaveTradeDetailsCheckVersionIsLessThanLastExixtedTradeVersionThenThrowsException() throws TradeStoeException {
		TradeStore tradeStore = new TradeStore("T1", 3, "CP-2", "B2", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N');
		TradeStoreRequest request=new TradeStoreRequest("T1", 3, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N');
		
		List<TradeStore> tradeStores = new ArrayList<>();
		tradeStores.add(new TradeStore("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N'));

		tradeStores.add(new TradeStore("T1", 2, "CP-2", "B1", LocalDate.now(), LocalDate.parse("2022-03-25"), 'N'));

		tradeStores.add(new TradeStore("T1", 3, "CP-1", "B2", LocalDate.now(), LocalDate.parse("2022-02-10"), 'N'));

		tradeStores.add(new TradeStore("T1", 4, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-01-30"), 'N'));

		
		
		
		when(repository.save(tradeStore)).thenReturn(tradeStore);
		when(repository.findByTradeId(request.getTradeId())).thenReturn(tradeStores);
		
		String message="Not Acceptable : This Trade Version :" + request.getVersion()
		+ " is Lower than last existance Trade";
		assertThrows(TradeStoeException.class, ()->{
			tradeStoreService.saveTradeDetails(request);
		},message);
		
	
	}
	
	

}
