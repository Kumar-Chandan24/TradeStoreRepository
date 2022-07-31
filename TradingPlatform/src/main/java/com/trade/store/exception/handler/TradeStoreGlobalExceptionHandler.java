package com.trade.store.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trade.store.exception.TradeStoeException;

@RestControllerAdvice
public class TradeStoreGlobalExceptionHandler {

	@ExceptionHandler(value = {TradeStoeException.class})
	public ResponseEntity<String> handelTradeStoreException(TradeStoeException exception){
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
