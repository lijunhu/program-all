package program.tiger.sword.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author junhu.li
 * @ClassName ExportAppUk
 * @Description TODO
 * @date 2019-10-3019:50
 * @Version 1.0.0
 */
public class ExportAppUk {


    private static final String groupId = "5d4fd6889725eb00076744c4";
    private static final String janusToken = "5d07036b34572400072b4a70";
    private static final String janusHost = "http://janus.t.17usoft.com";

    public static void main(String[] args) throws Exception {
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("jean", "8abc4ed337cf4da0907ea5210d203191");
        cookie.setDomain("jean.corp.elong.com");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        //查询janus appServer 信息
        HttpGet janusAppServerGet = new HttpGet(janusHost + "/janus-api/api/server/" + groupId + "/list?pageSize=200&pageNum=1");
        janusAppServerGet.setHeader("user-token", janusToken);
        HttpResponse httpResponse = httpClient.execute(janusAppServerGet);

        HttpPut put = new HttpPut();
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream bos  = new ByteArrayOutputStream();
        workbook.write(bos);
        bos.flush();
        put.setEntity(new InputStreamEntity(new ByteArrayInputStream(bos.toByteArray())));

        String respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JanusAppServer.AppServerResponse janusAppServerResponse = JsonUtil.toBean(respStr, JanusAppServer.AppServerResponse.class);

        for (JanusAppServer app : janusAppServerResponse.getResult().getPageData()) {

            if (app.isUseNameServer()) {
                HttpGet jeanGet = new HttpGet("http://jean.corp.elong.com/ocean/api/node-search?name=" + app.getNameServer() + "&flag=false");

                httpResponse = httpClient.execute(jeanGet);
                respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                JeanResponse.AppInfoResponse jeanAppResponse = JsonUtil.toBean(respStr, JeanResponse.AppInfoResponse.class);
                if (jeanAppResponse.getData().size() > 0) {
                    System.out.println(app.getId() + "\t\t" + jeanAppResponse.getData().get(0).getUk());
                }
            }
        }

    }
}
