package com.nttdata.bootcamp.client.entity;

import java.io.Serializable;
import java.util.ArrayList;
import lombok.Data;

@Data
public class RequestClientDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8012910039454796311L;
	
	private String id;
	private String name;
	private String lastname;
	private String dni;
	private String type;
	private Product product;
	
	public Client toClient()
	{
		//List<Product> detail = new ArrayList<Product>();
		//detail.add(product);
		return Client.builder()
				.name(name)
				.lastname(lastname)
				.dni(dni)
				.type(type)
				.detail(new ArrayList<Product>())
				.build();
	}
	
	public Client toClient(Client cli)
	{
		cli.setDni(dni);
		cli.setType(type);
		cli.setDetail(cli.getDetail());
		return cli;
	}
	
	public Client toClientUpdate(Client cli)
	{
		cli.setDetail(cli.getDetail());
		return cli;
	}
	
}
