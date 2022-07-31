package com.trade.store.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.trade.store.binding.TradeStoreRequest;
import com.trade.store.binding.TradeStoreResponse;
import com.trade.store.service.TradeStoreService;

@WebMvcTest
public class TradeManagementControllerTest {

	@MockBean
	private TradeStoreService storeService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testForGetAllTradeRecord() throws Exception {
		List<TradeStoreResponse> responses = new ArrayList<>();
		responses.add(
				new TradeStoreResponse("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-04-25"), 'N'));

		responses.add(
				new TradeStoreResponse("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-03-25"), 'N'));

		responses.add(
				new TradeStoreResponse("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-02-10"), 'N'));

		responses.add(
				new TradeStoreResponse("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.parse("2022-01-30"), 'N'));

		when(storeService.getAllTradeRecord()).thenReturn(responses);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/getAllTrades");
		ResultActions perform = mockMvc.perform(builder);
		MvcResult mvcResult = perform.andReturn();

		MockHttpServletResponse servletResponse = mvcResult.getResponse();
		int status = servletResponse.getStatus();
		assertEquals(200, status);

	}

	@Test
	public void testForSaveTradeRecord() throws Exception {
		TradeStoreRequest storeRequest = new TradeStoreRequest();
		storeRequest.setTradeId("T1");
		storeRequest.setVersion(1);
		storeRequest.setCounterPartyId("CP-1");
		storeRequest.setBookId("B1");
		storeRequest.setMaturityDate(LocalDate.parse("2022-09-23"));
		storeRequest.setCreatedDate(LocalDate.parse("2022-02-03"));
		storeRequest.setIsExpired('N');

		ObjectMapper mapper = new ObjectMapper();

		JavaTimeModule timeModule = new JavaTimeModule();
		timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
		mapper.registerModule(timeModule);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		String input = mapper.writeValueAsString(storeRequest);
		String expectedResult="Trade Details Store SuccessFully";

		when(storeService.saveTradeDetails(storeRequest)).thenReturn(expectedResult);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/saveTrade")
				.contentType("application/json").content(input);

		MvcResult result = mockMvc.perform(builder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		int status = response.getStatus();
		
		
		assertEquals(201, status);
		assertNotEquals(400, status);
		

	}

}
