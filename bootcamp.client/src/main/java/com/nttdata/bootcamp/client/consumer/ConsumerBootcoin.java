package com.nttdata.bootcamp.client.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.bootcamp.client.Util;
import com.nttdata.bootcamp.client.dao.ClientRepository;
import com.nttdata.bootcamp.client.entity.MessageTransactWallet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
public class ConsumerBootcoin {
	
	private final KafkaTemplate<Integer, String> template;
	
	@Autowired
	ClientRepository clientRepository;
	@KafkaListener(topics={"registerBootcoin"}, groupId="spring-boot-kafka-client")
	public void consumeRegisterBootcoin(String message) throws JsonProcessingException {
		MessageTransactWallet messageResult = Util.objectMapper.readValue(message, MessageTransactWallet.class);
		var resultClient = clientRepository.findByDni(messageResult.getDestination()).block();
		if(resultClient!=null)
		{
			var findCorrienteOrAhorro = false;
			for(int i=0; i<resultClient.getDetail().size(); i++)
			{
				if(resultClient.getDetail().get(i).getLimit_transaction()==0.0
						|| resultClient.getDetail().get(i).getDeposit_date().equals("-"))
					findCorrienteOrAhorro = true;
				if(resultClient.getDetail().get(i).getLimit_transaction()!=0.0
						|| resultClient.getDetail().get(i).getDeposit_date().equals("-"))
					findCorrienteOrAhorro = true;
				if(findCorrienteOrAhorro)
					break;
			}
			if(findCorrienteOrAhorro)
			{
				messageResult.setStatus("SuccessSeller");
				template.send("registerSelletBootcoin", messageResult.toString());
			}
			else
			{
				messageResult.setStatus("FailedSeller");
				template.send("registerSelletBootcoin", messageResult.toString());
			}
				
				
		}
	}
	
}
