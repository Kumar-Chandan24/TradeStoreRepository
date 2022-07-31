package com.trade.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradingPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingPlatformApplication.class, args);
	}

}
