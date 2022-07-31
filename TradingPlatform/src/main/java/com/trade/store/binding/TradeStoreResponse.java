package com.trade.store.binding;

import java.io.Serializable;
import java.time.LocalDate;


public class TradeStoreResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tradeId;
	private Integer version;
	private String counterPartyId;
	private String bookId;
	private LocalDate maturityDate;
	private LocalDate createdDate;
	private Character isExpired;
	
	public TradeStoreResponse() {
		super();
	}

	public TradeStoreResponse(String tradeId, Integer version, String counterPartyId, String bookId,
			LocalDate maturityDate, LocalDate createdDate, Character isExpired) {
		super();
		this.tradeId = tradeId;
		this.version = version;
		this.counterPartyId = counterPartyId;
		this.bookId = bookId;
		this.maturityDate = maturityDate;
		this.createdDate = createdDate;
		this.isExpired = isExpired;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCounterPartyId() {
		return counterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public Character getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Character isExpired) {
		this.isExpired = isExpired;
	}

	
	
	
	

}
