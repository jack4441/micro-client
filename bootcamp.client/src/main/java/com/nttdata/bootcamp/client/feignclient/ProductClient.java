package com.nttdata.bootcamp.client.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import com.nttdata.bootcamp.client.entity.ResponseProduct;

import reactor.core.publisher.Flux;

@FeignClient(name = "service-product")
public interface ProductClient {
	
	@GetMapping(path = "/product/v1/allproduct", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ResponseProduct> getAll();

}
