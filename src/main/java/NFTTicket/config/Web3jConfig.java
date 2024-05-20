package NFTTicket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Web3jConfig {
    @Value("${infura.api_url}")
    private String INFURA_API_URL;

    @Value("${sepolia.private_key}")
    private String PRIVATE_KEY;

    @Value("${metamask.wallet_address}")
    private String WALLET_KEY;
}
