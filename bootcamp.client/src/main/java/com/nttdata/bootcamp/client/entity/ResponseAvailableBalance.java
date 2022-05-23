package com.nttdata.bootcamp.client.entity;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;


@Data
public class ResponseAvailableBalance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7470687252787311213L;
	private String idclient;
	private String idproduct;
	private double available_balance;
	private String number_descrip_product;

}
