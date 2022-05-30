package com.nttdata.bootcamp.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nttdata.bootcamp.client.controller.ControllerClient;
import com.nttdata.bootcamp.client.dao.ClientRepository;
import com.nttdata.bootcamp.client.entity.Accounts;
import com.nttdata.bootcamp.client.entity.Client;
import com.nttdata.bootcamp.client.entity.Product;
import com.nttdata.bootcamp.client.entity.RequestClientDto;
import com.nttdata.bootcamp.client.entity.ResponseDelete;
import com.nttdata.bootcamp.client.service.ServiceClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@WebFluxTest(ControllerClient.class)
class ApplicationTests {
	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private ClientRepository clientRepository;
	@MockBean
	private ServiceClient service;
	
	@Test
	//1.- Registrar cliente personal con producto pasivo ahorro.
	public void addClientPersonalPasiveProductAhorro()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Maria");
		clientDto.setLastname("Del Carmen");
		clientDto.setDni("78666612");
		clientDto.setType("personal");
		Product product = new Product();
		product.setId("62853d233bb9de122e044c64");
		product.setCash(300.00);
		product.setModality("titular");
		product.setBank_account("444547878457898654");
		product.setInter_bank_account("00258887845789865487");
		product.setCompany("-");
		product.setCredit(0.00);
		product.setFee("-");
		product.setPayperfee(0.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(2);
		product.setDeposit_date("-");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//2.- Registrar cliente personal con producto pasivo plazo fijo.
	public void addClientPersonalPasiveProductPlazoFijo()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Maria");
		clientDto.setLastname("Del Carmen");
		clientDto.setDni("78666612");
		clientDto.setType("personal");
		Product product = new Product();
		product.setId("62853d413bb9de122e044c66");
		product.setCash(0.00);
		product.setModality("titular");
		product.setBank_account("442227878457898654");
		product.setInter_bank_account("00256687845789865466");
		product.setCompany("-");
		product.setCredit(0.00);
		product.setFee("-");
		product.setPayperfee(0.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(2);
		product.setDeposit_date("30-06-2022");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//3.- Registrar cliente personal con producto pasivo cuenta corriente.
	public void addClientPersonalPasiveProductCuentaCorriente()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Maria");
		clientDto.setLastname("Del Carmen");
		clientDto.setDni("78666612");
		clientDto.setType("personal");
		Product product = new Product();
		product.setId("62853d353bb9de122e044c65");
		product.setCash(8000.00);
		product.setModality("titular");
		product.setBank_account("442227878499998654");
		product.setInter_bank_account("00256687845799995466");
		product.setCompany("-");
		product.setCredit(0.00);
		product.setFee("-");
		product.setPayperfee(0.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(0);
		product.setDeposit_date("-");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//4.- Registrar cliente empresarial titular con producto pasivo cuenta corriente.
	public void addClientEmpresarialTitularPasiveProductCuentaCorriente()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Jorge");
		clientDto.setLastname("Paredes");
		clientDto.setDni("78661111");
		clientDto.setType("empresarial");
		Product product = new Product();
		product.setId("62853d353bb9de122e044c65");
		product.setCash(10000.00);
		product.setModality("titular");
		product.setBank_account("441515878499998654");
		product.setInter_bank_account("00251515845799995466");
		product.setCompany("NTTDATA");
		product.setCredit(0.00);
		product.setFee("-");
		product.setPayperfee(0.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(0);
		product.setDeposit_date("-");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//5.- Registrar cliente empresarial firmante autorizado con producto pasivo cuenta corriente.
	public void addClientEmpresarialAuthorizedPasiveProductCuentaCorriente()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Maribel");
		clientDto.setLastname("Soldeño");
		clientDto.setDni("78986588");
		clientDto.setType("empresarial");
		Product product = new Product();
		product.setId("62853d353bb9de122e044c65");
		product.setCash(10000.00);
		product.setModality("autorizado");
		product.setBank_account("441515878499998654");
		product.setInter_bank_account("00251515845799995466");
		product.setCompany("NTTDATA");
		product.setCredit(0.00);
		product.setFee("-");
		product.setPayperfee(0.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(0);
		product.setDeposit_date("-");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//6.- Registrar cliente personal con producto activo crédito personal sin una cuenta bancaria.
	public void addClientPersonalActiveProductNotHaveBankAccount()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Salvador");
		clientDto.setLastname("Roma");
		clientDto.setDni("78987777");
		clientDto.setType("personal");
		Product product = new Product();
		product.setId("62853d533bb9de122e044c67");
		product.setCash(0.00);
		product.setModality("titular");
		product.setBank_account("-");
		product.setInter_bank_account("-");
		product.setCompany("-");
		product.setCredit(24000.00);
		product.setFee("12 meses");
		product.setPayperfee(2000.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(0);
		product.setDeposit_date("-");
		product.setPayment_date("30-05-2022");
		//Posee una cuenta vencida.
		product.setCount_overdue_installments(1);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//7.- Registrar cliente empresarial con producto activo crédito empresarial sin una cuenta bancaria.
	public void addClientEmpresarialActiveProductNotHaveBankAccount()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Ramon");
		clientDto.setLastname("Rojas");
		clientDto.setDni("78881127");
		clientDto.setType("empresarial");
		Product product = new Product();
		product.setId("62853d5c3bb9de122e044c68");
		product.setCash(0.00);
		product.setModality("titular");
		product.setBank_account("-");
		product.setInter_bank_account("-");
		product.setCompany("NTTDATA");
		product.setCredit(24000.00);
		product.setFee("12 meses");
		product.setPayperfee(2000.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(0);
		product.setDeposit_date("-");
		product.setPayment_date("02-06-2022");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//8.- Registrar cliente personal o empresarial con producto activo tarjeta de crédito sin una cuenta bancaria.
	public void addClientActiveProductCreditCardNotHaveBankAccount()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Juana");
		clientDto.setLastname("Horcara");
		clientDto.setDni("78945678");
		clientDto.setType("personal");
		Product product = new Product();
		product.setId("62853d6b3bb9de122e044c69");
		product.setCash(0.00);
		product.setModality("titular");
		product.setBank_account("-");
		product.setInter_bank_account("-");
		product.setCompany("-");
		product.setCredit(2000.00);
		product.setFee("12 meses");
		product.setPayperfee(2000.00);
		product.setCredit_card("4555-4444-4444-4444");
		product.setCcv("444");
		product.setExpiration_date("12/2025");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(0);
		product.setDeposit_date("-");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//9.- Registrar cliente VIP.
	public void addClientVIP()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Juana");
		clientDto.setLastname("Horcara");
		clientDto.setDni("78945678");
		clientDto.setType("VIP");
		Product product = new Product();
		product.setId("62853d233bb9de122e044c64");
		product.setCash(5000.00);
		product.setModality("titular");
		product.setBank_account("478547878457898666");
		product.setInter_bank_account("00254787845789865666");
		product.setCompany("-");
		product.setCredit(0.00);
		product.setFee("-");
		product.setPayperfee(2000.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(400.00);
		product.setLimit_transaction(3);
		product.setDeposit_date("-");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//10.- Registrar cliente PYME.
	public void addClientPYME()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Juana");
		clientDto.setLastname("Horcara");
		clientDto.setDni("78945678");
		clientDto.setType("PYME");
		Product product = new Product();
		product.setId("62853d353bb9de122e044c65");
		product.setCash(50000.00);
		product.setModality("titular");
		product.setBank_account("478547878457894848");
		product.setInter_bank_account("00254787845789869999");
		product.setCompany("NTTDATA");
		product.setCredit(0.00);
		product.setFee("-");
		product.setPayperfee(2000.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(0);
		product.setDeposit_date("-");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//10.- Registrar cliente Débito.
	public void addClientDebito()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Jose");
		clientDto.setLastname("Diestra");
		clientDto.setDni("78945612");
		clientDto.setType("personal");
		Product product = new Product();
		product.setId("629189ef34033978a275432f");
		product.setCash(0.00);
		product.setModality("titular");
		product.setBank_account("478547878457898654");
		product.setInter_bank_account("00254787845789865487");
		product.setCompany("-");
		product.setCredit(0.00);
		product.setFee("-");
		product.setPayperfee(2000.00);
		product.setCredit_card("4555-4444-4444-8888");
		product.setCcv("441");
		product.setExpiration_date("12/2025");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(0);
		product.setDeposit_date("-");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		Accounts accounts1 = new Accounts();
		accounts1.setAccount("478547878457898223");
		accounts1.setInter_account("00254787845789861221");
		Accounts accounts2 = new Accounts();
		accounts2.setAccount("478547878457894444");
		accounts2.setInter_account("00254787845789864141");
		List<Accounts> accounts = new ArrayList<>();
		accounts.add(accounts1);
		accounts.add(accounts2);
		product.setBank_accounts(accounts);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.post().uri("/client/v1/saveclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//11.- Actualizar Cliente.
	public void updateClient()
	{
		RequestClientDto clientDto = new RequestClientDto();
		clientDto.setName("Jorge");
		clientDto.setLastname("Paredes");
		clientDto.setDni("78661111");
		clientDto.setType("empresarial");
		Product product = new Product();
		product.setId("62853d353bb9de122e044c65");
		product.setCash(10000.00);
		product.setModality("titular");
		product.setBank_account("441515878499998654");
		product.setInter_bank_account("00251515845799995466");
		product.setCompany("NTTDATA");
		product.setCredit(0.00);
		product.setFee("-");
		product.setPayperfee(0.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(0);
		product.setDeposit_date("-");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		clientDto.setProduct(product);
		
		
		
		when(service.clientSave(clientDto)).thenReturn(Mono.just(clientDto.toClient()));
		
		webTestClient.put().uri("/client/v1/updateclient")
			.bodyValue(clientDto)
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//12.- Eliminar Cliente.
	public void deleteClient()
	{
		String idclient = "6291b4ce46baeb2bf73b1723";
		ResponseDelete reponse = ResponseDelete.builder().response("Operación completada").build();
		given(service.clientDelete(any())).willReturn(Mono.just(reponse));
		webTestClient.delete().uri("/client/v1/deleteclient/6291b4ce46baeb2bf73b1723")
			.exchange()
			.expectStatus().isOk();
		
	}
	
	@Test
	//12.- Listar Clientes.
	public void allClients()
	{
		Product prod = new Product();
		Product product = new Product();
		product.setIddetail("883df67b-cca5-4b8d-9165-dac60cfe2db2");
		product.setId("62853d233bb9de122e044c64");
		product.setCash(0.00);
		product.setModality("titular");
		product.setBank_account("478547878457898654");
		product.setInter_bank_account("00254787845789865487");
		product.setCompany("-");
		product.setCredit(0.00);
		product.setFee("-");
		product.setPayperfee(0.00);
		product.setCredit_card("-");
		product.setCcv("-");
		product.setExpiration_date("-");
		product.setState("active");
		product.setCounter_transaction(0);
		product.setBalance_day(0.00);
		product.setLimit_transaction(2.0);
		product.setDeposit_date("-");
		product.setPayment_date("-");
		product.setCount_overdue_installments(0);
		product.setBank_accounts(null);
		List<Product> products = new ArrayList<>();
		products.add(product);
		Flux<Client> clients = Flux.just(Client.builder().id("6293fff2e67c141cf04875de")
								.name("Jose")
								.lastname("Diestra")
								.dni("78945612")
								.type("personal")
								.detail(products)
								.build());
		when(service.clientFindAll()).thenReturn(clients);
		Flux<Client> responseBody = webTestClient.get().uri("/client/v1/allclient")
				.exchange()
				.expectStatus().isOk()
				.returnResult(Client.class)
				.getResponseBody();
		StepVerifier.create(responseBody)
		.expectSubscription()
		.expectNext(Client.builder().id("6293fff2e67c141cf04875de")
								.name("Jose")
								.lastname("Diestra")
								.dni("78945612")
								.type("personal")
								.detail(products)
								.build()).verifyComplete();
	}

}
