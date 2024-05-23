package NFTTicket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@SpringBootApplication
public class NftTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(NftTicketApplication.class, args);
	}

}
