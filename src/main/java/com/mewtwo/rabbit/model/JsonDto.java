package com.mewtwo.rabbit.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class JsonDto implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UUID idTransaction;
	public LocalDateTime transactionDate;
	public String document;
	public String name;
	public String age;
	public String amount;
	public String installmentNumber;
	
}
