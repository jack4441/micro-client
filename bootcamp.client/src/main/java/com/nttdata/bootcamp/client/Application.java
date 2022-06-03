package com.nttdata.bootcamp.client;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nttdata.bootcamp.client.dao.ClientRepository;
import com.nttdata.bootcamp.client.entity.Client;
import com.nttdata.bootcamp.client.entity.Product;
import com.nttdata.bootcamp.client.entity.RequestClientDto;
import com.nttdata.bootcamp.client.service.IServiceClient;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
//@RefreshScope
@EnableReactiveMongoRepositories(basePackageClasses = ClientRepository.class)
//@EnableKafkaStreams
@RequiredArgsConstructor
public class Application {

	@Autowired
	IServiceClient serviceClient;
	
	Boolean doExecuteUpdate = false;
	Boolean doExecuteDelete = false;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	//private final KafkaTemplate<String, String> template;
	
	/*@Component
	class Consumer{
		
		@KafkaListener(topics={"client"}, groupId="spring-boot-kafka-client")
		public void consume(ConsumerRecord<String, String> record) throws JsonMappingException, JsonProcessingException
		{
			System.out.println("received = " + record.value() + " con key " + record.key());
			var arrayIds = record.key().split(",");
			RequestClientDto request = new RequestClientDto();
			if(record.value().equals("UCD"))
			{
				
				log.info("Se actualizará el detalle del cliente: " + arrayIds[0]);				
				serviceClient.clientFindOne(arrayIds[0]).doOnSuccess(value->{
					if(value!=null)
					{
						log.info("Se encontró al cliente: " + value.getName());
						value.getDetail().add(arrayIds[1]);
						
						var clie = Client.builder()
								.id(value.getId())
								.name(value.getName())
								.lastname(value.getLastname())
								.dni(value.getDni())
								.type(value.getType())
								.detail(value.getDetail())
								.build();
						request.setClient(clie);
						doExecuteUpdate=true;
					}
				}).block();
				if(doExecuteUpdate)
				{
					serviceClient.clientUpdate(request).doOnSuccess(valueUpdate-> {
						log.info("Se actualizó correctamente el detalle del cliente: " + valueUpdate.getName());
					}).block();
				}
			}
			if(record.value().equals("DC"))
			{
				log.info("Se eliminará el cliente con id: " + arrayIds[0]);
				serviceClient.clientDelete(arrayIds[0]).doOnSuccess(value-> {
					log.info("Se eliminó el cliente con id: " + arrayIds[0]);
				}).block();
			}
		}
	}*/
	
	/*@Component
	class Processor
	{
		
		@Autowired
		public void process(StreamsBuilder builder)
		{
			
			final Serde<Integer> integerSerde = Serdes.Integer();
			final Serde<String> stringSerde = Serdes.String();
			final Serde<Long> longSerde = Serdes.Long();
			
			KStream<String, String> textLines = builder
					.stream("client", Consumed.with(stringSerde, stringSerde));
			
			textLines.foreach((key, value)-> {
				if(value == "")
			});
			
			KTable<String, String> wordCounts = textLines.toTable();
			wordCounts.toStream().to("detailclient", Produced.with(stringSerde, stringSerde));
						
		}
		
	}*/

}
