package com.nttdata.bootcamp.client.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Document("client")
@Data
@Builder
public class Client implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6408698684162133128L;
	@Id
	private String id;
	@Field
	private String name;
	@Field
	private String lastname;
	@Field
	private String dni;
	@Field
	private String type;
	@Field
	private List<Product> detail;
	
	//Método gestor de las validaciones al registrar un cliente
	public boolean filterRegisterClient(String idProduct, String modality, List<ResponseProduct> productsPasive, List<ResponseProduct> productsActive
			, List<Client> clientes)
	{
		log.info("Entrando al método filterRegisterClient en la clase Client");

		var result = false;
		if(productsPasive.stream().filter(value -> value.getId().equals(idProduct)).findAny().isPresent()
				|| productsActive.stream().filter(value -> value.getId().equals(idProduct)).findAny().isPresent())
		{
			if(isProductPasive(idProduct, productsPasive))
			{
				log.info("Entrando al filtro de productos pasivos");
				if(this.type.equals("personal"))
				{
					log.info("Se detectó al cliente como cliente personal");
					result = filterProductPasiveClientPersonal(idProduct);
				}
				if(this.type.equals("empresarial"))
				{
					log.info("Se detectó al cliente como cliente empresarial");
					result = filterTitularAuthorizedProductPasiveClientBusiness(idProduct, productsPasive, modality, clientes);
				}
			}
			if(isProductActive(idProduct, productsActive))
			{
				log.info("Entrando al filtro de productos activos");
				result = filterProductClientForGetProductCredit(productsPasive);
			}			
		}

		log.info("El resultado del registro es: " + result);
		return result;
	}
	
	// Un cliente personal solo puede tener un máximo de una cuenta de ahorro,
	// una cuenta corriente o cuentas a plazo fijo.
	// Si es un producto pasivo
	// Si es un cliente personal	
	private boolean filterProductPasiveClientPersonal(String idProduct)
	{
		log.info("Entrando al método filterProductPasiveClientPersonal en la clase Client");
		var result = false;
		var findProduct = new Product();
		if(!this.detail.isEmpty())
		{
			log.info("Este cliente personal posee productos");
			findProduct = this.detail.stream().filter(value -> value.getId().equals(idProduct)).findAny().orElse(null);
		}			
		if(findProduct==null) result = true;
		if(this.detail.isEmpty()) result = true;
		return result;
	}
	
	// Un cliente empresarial no puede tener una cuenta de ahorro o de plazo fijo,
	// pero sí múltiples cuentas corrientes.
	// Si es un producto pasivo
	// Si es un cliente empresarial
	private boolean filterProductPasiveClientBusiness(String idProduct, List<ResponseProduct> productsPasive)
	{
		var result = false;
		if(idProduct.equals(getCuentaCorriente(productsPasive)))
		{
			result = true;
		}
		return result;
	}
	
	// Las cuentas bancarias empresariales pueden tener uno o más titulares y cero
	// o más firmantes autorizados.
	// Si es un producto pasivo
	// Si es un cliente empresarial
	// Si es titular sin productos pasivos registrado
	// o
	// si es firmante autorizado con productos pasivos registrados anteriormente.
	private boolean filterTitularAuthorizedProductPasiveClientBusiness(String idProduct, List<ResponseProduct> productsPasive, String modality
			, List<Client> clientes)
	{
		var result = false;
		var resultFind = false;
		
		if(!clientes.isEmpty() && modality.equals("autorizado"))
		{
			log.info("Este cliente empresarial no tiene productos pasivos y se va a registrar con una cuenta bancaria existente como " + modality);
			result = filterProductPasiveClientBusiness(idProduct, productsPasive);
		}
		else
		{
			if(detail.isEmpty() && modality.equals("titular"))
			{
				log.info("Este cliente empresarial no tiene productos pasivos y es " + modality);
				result = filterProductPasiveClientBusiness(idProduct, productsPasive);
			}			
			if(!detail.isEmpty())
			{
				log.info("Este cliente empresarial tiene productos pasivos y es " + modality);
				resultFind = productsPasive.stream()
				// Iteramos los productos pasivos
				.map(value -> this.detail.stream()
						// buscamos si hay productos pasivos dentro del detalle del cliente empresarial
						.filter(element -> element.getId().equals(value.getId()))).findAny().isPresent();
			}
		}

		if(resultFind)
			result = filterProductPasiveClientBusiness(idProduct, productsPasive);
		return result;
	}
	
	// Un cliente puede tener un producto de crédito sin la obligación de tener una
	// cuenta bancaria en la institución.
	private boolean filterProductClientForGetProductCredit(List<ResponseProduct> productsPasive)
	{
		var result = false;
		var resultFind = false;
		resultFind = productsPasive.stream()
				// Iteramos los productos pasivos
				.map(value -> this.detail.stream()
						// buscamos si hay productos pasivos dentro del detalle del cliente empresarial
						.filter(element -> element.getId().equals(value.getId()))).findAny().isPresent();
		// Si el cliente no tiene productos pasivos puede registrar su producto 
		if(resultFind) result = true;
		else result = true;
		return result;
	}
	
	// Método auxiliar
	private boolean isProductPasive(String idProduct, List<ResponseProduct> productsPasive)
	{
		var result = false;
		result = productsPasive.stream()
				.filter(value -> value.getId().equals(idProduct)).findAny().isPresent();
		return result;
	}
	
	// Método auxiliar
	private boolean isProductActive(String idProduct, List<ResponseProduct> productsActive)
	{
		var result = false;
		result = productsActive.stream()
				.filter(value -> value.getId().equals(idProduct)).findAny().isPresent();
		return result;
	}
	
	// Método auxiliar
	private String getCuentaCorriente(List<ResponseProduct> productsPasive)
	{
		return productsPasive.stream().filter(value -> value.getDescription().equals("cuenta corriente")).findAny().get().getId();
	}
	
	//Método auxiliar
	private String getCreditCard(List<ResponseProduct> productsActive)
	{
		return productsActive.stream().filter(value -> value.getDescription().equals("tarjeta de credito")).findAny().get().getId();
	}
	
	// Cuenta corriente sin comisión de mantenimiento.
	// Como requisito, el cliente debe tener una tarjeta
	// de crédito con el banco al momento de la creación de la cuenta.
	private boolean filterProductPasiveClientBusinessPYME(String idProduct, List<ResponseProduct> productsActive)
	{
		var result = false;
		return result;
	}
	
}
