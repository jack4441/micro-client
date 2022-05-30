package com.nttdata.bootcamp.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.nttdata.bootcamp.client.dao.ClientRepository;
import com.nttdata.bootcamp.client.entity.Client;
import com.nttdata.bootcamp.client.entity.Product;
import com.nttdata.bootcamp.client.entity.RequestClientDto;
import com.nttdata.bootcamp.client.entity.RequestClientUpdateDto;
import com.nttdata.bootcamp.client.entity.ResponseAvailableBalance;
import com.nttdata.bootcamp.client.entity.ResponseDelete;
import com.nttdata.bootcamp.client.feignclient.ProductClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class ServiceClient implements IServiceClient {

	@Autowired
	ClientRepository clientdao;
	@Autowired
	ProductClient productdao;

	
	@Override
	public Flux<Client> clientFindAll() {
		//Añadir manejo de errores
		return  clientdao.findAll();
	}
	
	@Override
	public Flux<Client> clientFindAll(String bank_account) {
		//Añadir manejo de errores
		log.info("Entrando al metodo clientFindAll en el servicio ServiceClient.");
		return  clientdao.findAll().filter(value -> value.getDetail().stream().map(detail -> detail.getBank_account().equals(bank_account)).count()>0);
	}

	@Override
	public Mono<ResponseAvailableBalance> findCashOrCredit(String id, String bank_account, String credit_card) {
		// TODO Auto-generated method stub
		
		ResponseAvailableBalance result = new ResponseAvailableBalance();
		
		 clientdao.findById(id).doOnSuccess(value -> {
			 value.getDetail().forEach(el ->{
				 if((el.getBank_account().equals(bank_account)||el.getCredit_card().equals(credit_card))
						 && el.getId().equals(id))
				 {
					 if(!bank_account.isEmpty()) { result.setIdproduct(id); result.setIdproduct(el.getId())
					 ; result.setNumber_descrip_product(bank_account); result.setAvailable_balance(el.getCash()); }
					 if(!credit_card.isEmpty()) { result.setIdproduct(id); result.setIdproduct(el.getId())
					 ; result.setNumber_descrip_product(credit_card); result.setAvailable_balance(el.getCredit()); }
				 }
			 });
		 });
		 return Mono.just(result);
	}
	
	@Override
	public Mono<Client> clientFindSingleReceiver(String destination) {
		// TODO Auto-generated method stub
		return clientdao.findAll().filter(value-> value.getDetail().stream().filter(valueDetail-> 
			valueDetail.getBank_account().equals(destination)
			|| valueDetail.getIddetail().equals(destination)).count()>0).singleOrEmpty();
	}
	
	private Flux<Client> clientFindReceiver(String destination) {
		// TODO Auto-generated method stub
		return clientdao.findAll().filter(value-> value.getDetail().stream().filter(valueDetail-> 
			valueDetail.getBank_account().equals(destination)
			|| valueDetail.getIddetail().equals(destination)).count()>0);
	}
	
	@CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallbacksave")
	@Retry(name = "myRetry")
	@Override
	public Mono<Client> clientSave(RequestClientDto request) {	
	
		request.getProduct().setIddetail(UUID.randomUUID().toString());
		log.info("Entrando al metodo clientSave en el servicio ServiceClient");
		var listcli = clientFindReceiver(request.getProduct().getBank_account()).collectList().block();
		var cli = clientdao.findByDni(request.getDni()).block();
		var result = false;
		if(cli==null)
		{
			cli = request.toClient();
			log.info("Entrando al metodo clientSave en el servicio ServiceClient");
			result = cli.filterRegisterClient(request.getProduct().getId(), request.getProduct().getModality()
					, productdao.getAll().stream().filter(value -> value.getCategory().equals("pasivo")).collect(Collectors.toList())
					, productdao.getAll().stream().filter(value -> value.getCategory().equals("activo")).collect(Collectors.toList())
					, listcli);
			List<Product> detail = new ArrayList<Product>();
			detail.add(request.getProduct());
			cli.setDetail(detail);
		}
		else
		{
			cli = request.toClient(cli);
			result = cli.filterRegisterClient(request.getProduct().getId(), request.getProduct().getModality()
					, productdao.getAll().stream().filter(value -> value.getCategory().equals("pasivo")).collect(Collectors.toList())
					, productdao.getAll().stream().filter(value -> value.getCategory().equals("activo")).collect(Collectors.toList())
					, listcli);
			cli.getDetail().add(request.getProduct());		
		}
		if(result)
			return clientdao.save(cli);
		else
			return Mono.just(Client.builder().build());
			
	}

	@Override
	public Mono<Client> clientUpdate(RequestClientUpdateDto request) {
		// TODO Auto-generated method stub
		
		log.info("Entrando al metodo clientUpdate en el servicio ServiceClient");
		if(request.getId()!=null)
			return clientdao.findById(request.getId()).map(value -> request.toClientUpdate(value)).flatMap(clientdao::save);
			//Añadir manejo de errores
		else
			return Mono.just(Client.builder().build());

	}

	@Override
	public Mono<ResponseDelete> clientDelete(String id) {
		log.info("Entrando al metodo clientDelete en el servicio ServiceClient");
		clientdao.deleteById(id).block();
		return Mono.just(ResponseDelete.builder().response("Operación completada").build());
	}

	@Override
	public Mono<Client> clientFindOne(String id) {
		// TODO Auto-generated method stub
		log.info("Entrando al metodo clientFindOne en el servicio ServiceClient.");
		return clientdao.findById(id);
	}
	
	
	public Mono<Client> fallbacksave(Exception e)
	{
    	log.info("Entrando al método fallbacksave en el servicio ServiceClient");
    	log.info("message Error: " + e.getMessage());
		return Mono.just(Client.builder().build());
	}
	
}
