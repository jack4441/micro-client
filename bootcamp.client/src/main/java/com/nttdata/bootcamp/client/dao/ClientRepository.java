package com.nttdata.bootcamp.client.dao;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.nttdata.bootcamp.client.entity.Client;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {	
Mono<Client> findByDni(String dni);
}
