package program.tiger.sword.http.openapi.gateway;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

public class SendRequest {
    private static MessageDigest digest;
    private static final Logger log = LoggerFactory.getLogger(SendRequest.class);


    static {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("get MessageDigest failed ,", e);
        }
    }


    public static String generateSignature(String data, String appKey, String timestamp, String secretKey) {
        String md5 = "";
        md5 = Hex.encodeHexString(digest.digest((data + appKey).getBytes(StandardCharsets.UTF_8)));
        md5 = Hex.encodeHexString(digest.digest((timestamp + md5 + secretKey).getBytes(StandardCharsets.UTF_8)));
        return md5;
    }

    public static String timeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }


    @Test
    public void sendRequest() {
         long l =  Long.parseLong("12345678901234567");
        System.out.println(l);

        String a = "10.160.85.79:8083";
        String data = "{\"Version\":\"1.5\",\"Local\":\"zh_CN\",\"Request\":{\"LastId\":1123123123121231121,\"Count\":\"1000\"}}";
        String format = "json";
        String timestamp = timeStamp();
       String method = "openapi.batch.test";
       String user = "b581e7272d51426daebffc30a1f277c4";
       String appKey = "9c95d4b136a1fb03ea93a93387e523a8";
       String secretKey = "ac9e11819e726cc1dba7561b513222ca";
        /* String method = "hotel.incr.data";
        String user = "Agent1610343644574";
        String appKey = "cc3a9f0c9eeca44fc8f036e228763de1";
        String secretKey = "f4be3738c2c332f61ef5b5b542fbd711";*/
        String signature = generateSignature(data, appKey, timestamp, secretKey);

        String url = "http://10.116.3.10/rest" +
                String.format("?format=%s&data=%s&signature=%s&timestamp=%s&user=%s&method=%s",
                        format, URLEncoder.encode(data, StandardCharsets.UTF_8), signature, timestamp, user, method);
        log.info(url);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String out = "";
        long start = System.currentTimeMillis();
        try {
            HttpGet get = new HttpGet(url);
            get.setHeader("host","api.elong.com");
            CloseableHttpResponse httpResponse = httpClient.execute(get);
            InputStream is = httpResponse.getEntity().getContent();
            out = new BufferedReader(new InputStreamReader(is))
                    .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            log.error("execute request failed,", e);
        }

        log.info(String.format("\n%s cost time:%d", out, System.currentTimeMillis() - start));


    }


}
