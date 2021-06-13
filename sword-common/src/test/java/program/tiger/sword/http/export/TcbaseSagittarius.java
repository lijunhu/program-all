package program.tiger.sword.http.export;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.netty.http.client.HttpClient;

/**
 * @author junhu.li
 * @ClassName TcbaseSagittarius
 * @Description TODO
 * @date 2020/5/2119:18
 * @Version 1.0.0
 */
public class TcbaseSagittarius {

    private static final Logger logger = LoggerFactory.getLogger(TcbaseSagittarius.class);

    private static final String janusHost = "http://janus.17usoft.com";
    private static final String janusConfigUri = "/janus-api/api/uriconfig/5d4ac8d39725eb0007682790/config/list";
    private static final String janusConfigDetailUri = "/janus-api/api/uriconfig/5d4ac8d39725eb0007682790/config/detail";


    public static void main(String[] args) {

        HttpClient httpClient = HttpClient.create();
        String response = httpClient
                .headers(h -> h.set("user-token", "5d07036b34572400072b4a70"))
                .get()
                .uri(janusHost + janusConfigUri)
                .responseContent()
                .aggregate()
                .asString()
                .block();

        logger.info(response);
    }


}
