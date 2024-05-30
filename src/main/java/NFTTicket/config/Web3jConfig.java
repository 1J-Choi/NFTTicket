package NFTTicket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
public class Web3jConfig {
    @Value("${infura.api_url}")
    private String INFURA_API_URL;

    @Value("${sepolia.private_key}")
    private String PRIVATE_KEY;

    @Value("${metamask.wallet_address}")
    private String WALLET_KEY;

    @Value("${quicknode.http.provider.url}")
    private String quicknodeHttpProvKey;

    @Value("${metamask.contract_address}")
    private String CONTRACT_ADDRESS;

    @Bean
    public Web3j web3j(){
        return Web3j.build(new HttpService(INFURA_API_URL));

//        try {
//            BigInteger blockNumber = web3j.ethBlockNumber().send().getBlockNumber();
//            System.out.println("Latest Ethereum block number: " + blockNumber);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Bean
    public Credentials credentials() {
        BigInteger privateKeyInBT = new BigInteger(PRIVATE_KEY, 16);
        return Credentials.create(ECKeyPair.create(privateKeyInBT));
    }

//    @Bean
//    public NFT nft() {
//        BigInteger gasPrice = Contract.GAS_PRICE;
//        BigInteger gasLimit = Contract.GAS_LIMIT;
//        StaticGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
//
//        return NFT.load(CONTRACT_ADDRESS, web3j(), credentials(), gasProvider);
//    }

}
