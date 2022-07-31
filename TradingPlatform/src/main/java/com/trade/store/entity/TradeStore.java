package com.trade.store.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRADE_STORE")
public class TradeStore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "TRADE_NO")
	private Integer tradeNo;
	@Column(name = "Trade_Id")
	private String tradeId;
	@Column(name = "Version")
	private Integer version;
	@Column(name = "Counter_Party_Id")
	private String counterPartyId;
	@Column(name = "Book_Id")
	private String bookId;
	@Column(name = "Maturity_Date")
	private LocalDate maturityDate;
	@Column(name = "Created_Date")
	private LocalDate createdDate;
	@Column(name = "Expired")
	private Character isExpired;

	public TradeStore() {
		super();
	}

	public TradeStore(String tradeId, Integer version, String counterPartyId, String bookId, LocalDate maturityDate,
			LocalDate createdDate, Character isExpired) {
		super();

		this.tradeId = tradeId;
		this.version = version;
		this.counterPartyId = counterPartyId;
		this.bookId = bookId;
		this.maturityDate = maturityDate;
		this.createdDate = createdDate;
		this.isExpired = isExpired;
	}

	public Integer getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(Integer tradeNo) {
		this.tradeNo = tradeNo;
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
