package com.nttdata.bootcamp.client.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


@Data
public class RequestClientUpdateDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -2677517194101273278L;
	private String id;
	private String name;
	private String lastname;
	private String dni;
	private String type;
	private List<Product> detail;
	
	public Client toClientUpdate(Client cli)
	{
		cli.setDetail(this.detail);
		return cli;
	}
	
}
