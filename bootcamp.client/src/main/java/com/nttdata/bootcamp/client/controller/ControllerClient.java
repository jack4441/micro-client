package com.nttdata.bootcamp.client.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.client.entity.Client;
import com.nttdata.bootcamp.client.entity.RequestClientDto;
import com.nttdata.bootcamp.client.entity.RequestClientUpdateDto;
import com.nttdata.bootcamp.client.entity.ResponseAvailableBalance;
import com.nttdata.bootcamp.client.entity.ResponseDelete;
import com.nttdata.bootcamp.client.service.IServiceClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
//@RequiredArgsConstructor
@RequestMapping("client/v1")
public class ControllerClient {

	//private final KafkaTemplate<String, String> template;
	
	@Autowired
	IServiceClient serviceClient;
	

	@GetMapping(path = "/allclient", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Client> getAll()
	{
		log.info("Entrando al metodo getAll en el controller client ");
		//return Flux.just(serviceClient.clientFindAll().toStream().collect(Collectors.toList()));
		return serviceClient.clientFindAll();
	}
	

	@GetMapping("/findclient/{id}")
	public Mono<Client> getClient(@PathVariable String id)
	{
		log.info("Entrando al metodo getClient en el controller client");
		return serviceClient.clientFindOne(id);
	}
	
	@GetMapping("/findcashorcredit/{id}/{bank_account}/{credit_card}")
	public Mono<ResponseAvailableBalance> getCashOrCredit(@PathVariable String id
			, @PathVariable String bank_account, @PathVariable String credit_card)
	{
		log.info("Entrando al metodo getCashOrCredit en el controller client");
		return serviceClient.findCashOrCredit(id, bank_account, credit_card);
	}
	
	@PostMapping(path = "/saveclient", produces = MediaType.APPLICATION_JSON_VALUE
			, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Client> save(@RequestBody RequestClientDto body)
	{
		log.info("Entrando al metodo save en el controller client");
		return serviceClient.clientSave(body);
	}
	
	@PutMapping(path = "/updateclient", produces = MediaType.APPLICATION_JSON_VALUE
			, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Client> update(@RequestBody RequestClientUpdateDto body)
	{
		log.info("Entrando al metodo update en el controller client");
		return serviceClient.clientUpdate(body);
	}
	
	@DeleteMapping(path = "/deleteclient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseDelete> delete(@PathVariable String id)
	{
		log.info("Entrando al metodo delete en el controller client");
		return serviceClient.clientDelete(id);
	}
	
	@GetMapping(path = "/getReceiver/{destination}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Client> clientFindReceiver(@PathVariable String destination)
	{
		log.info("Entrando al metodo clientFindReceiver en el controller client");
		return serviceClient.clientFindSingleReceiver(destination);
	}
	
}
