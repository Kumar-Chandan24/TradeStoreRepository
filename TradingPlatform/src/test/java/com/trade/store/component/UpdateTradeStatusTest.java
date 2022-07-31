package com.trade.store.component;

import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UpdateTradeStatusTest {

	@MockBean
	private UpdateTradeStatus updateTradeStatus;

	@Test
	public void testUpdateTradeStatusByMaturityDate() {

		doNothing().when(updateTradeStatus).updateTradeStatusByMaturityDate();

		updateTradeStatus.updateTradeStatusByMaturityDate();

	}

}
