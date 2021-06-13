package program.tiger.sword.common.response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * @author junhu.li
 * @ClassName ReadFile
 * @Description TODO
 * @date 2019-07-2218:13
 * @Version 1.0.0
 */
public class ReadFile {

    private static String Url = "http://ci.elong.cn/job/PrivateAPICheck_iOS/ws/PrivateAPICheck_iOS/tmp/checkResult.xlsx";


    public static void main(String[] args) throws Exception {
        CookieStore cookieStore = new BasicCookieStore();

        cookieStore.addCookie(new BasicClientCookie("JSESSIONID","DB807ADFFAD2661E96B500C491978EA0"));
        HttpClientBuilder clientBuilder = HttpClientBuilder.create().setDefaultCookieStore(cookieStore);
        CloseableHttpClient httpClient = clientBuilder.build();

        HttpGet httpGet = new HttpGet(Url);
        HttpResponse response = httpClient.execute(httpGet);

        HttpEntity httpEntity = response.getEntity();
    }
}