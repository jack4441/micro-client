package com.nttdata.bootcamp.client.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Document("client")
@Data
public class Product implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -7955936580657429664L;

	private String iddetail;
	private String id;
	private double cash;
	private String modality;
	private String bank_account;
	private String inter_bank_account;
	private String company;
	private double credit;
	private String fee;
	private double payperfee;
	private String credit_card;
	private String ccv;
	private String expiration_date;
	private String state;	
	private double counter_transaction;
	
}
