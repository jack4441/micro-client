package com.nttdata.bootcamp.client.service;

import com.nttdata.bootcamp.client.entity.Client;
import com.nttdata.bootcamp.client.entity.RequestClientDto;
import com.nttdata.bootcamp.client.entity.RequestClientUpdateDto;
import com.nttdata.bootcamp.client.entity.ResponseAvailableBalance;
import com.nttdata.bootcamp.client.entity.ResponseDelete;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IServiceClient {

	Flux<Client> clientFindAll();
	
	Mono<Client> clientFindOne(String id);
	
	Mono<Client> clientSave(RequestClientDto request);
	
	Mono<Client> clientUpdate(RequestClientUpdateDto request);
	
	Mono<ResponseDelete> clientDelete(String id);
	
	Flux<Client> clientFindAll(String bank_account);
	
	Mono<ResponseAvailableBalance> findCashOrCredit(String id, String bank_account, String credit_card);
	
	Mono<Client> clientFindReceiver(String bank_account);

}
