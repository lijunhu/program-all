package program.tiger.sword.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author junhu.li
 * @ClassName UpdateValidateSandBox
 * @Description TODO
 * @date 2019-08-0111:18
 * @Version 1.0.0
 */
public class UpdateValidateSandBox {


    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet configGet = new HttpGet("http://janus.t.17usoft.com/janus-api/api/uriconfig/5d05ebb83457240007d1fa2e/config/list?pageSize=700");
        configGet.addHeader("user-token", "5d07036b34572400072b4a70");
        HttpResponse httpResponse = httpClient.execute(configGet);
        String respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JanusConfig.ConfigResponse configResponse = JsonUtil.toBean(respStr, JanusConfig.ConfigResponse.class);
        List<JanusConfig> configInfos = configResponse.result.pageData;
        for (int i = 0; i < configInfos.size(); i++) {
            JanusConfig.ValidateRequest validateRequest = configInfos.get(i).getValidateRequest();
            if (validateRequest != null && validateRequest.getUsing()) {
                JanusConfig.RequestValidateSandbox requestSandbox = new JanusConfig.RequestValidateSandbox();
                requestSandbox.setUsing(true);
                requestSandbox.setValidateType("mapi");
                configInfos.get(i).setRequestValidateSandbox(requestSandbox);
            }

        }

        HttpPut put = new HttpPut("http://janus.t.17usoft.com/janus-api/api/uriconfig/5d05ebb83457240007d1fa2e/config");
        put.addHeader("Content-Type", "application/json");
        put.addHeader("user-token", "5d07036b34572400072b4a70");
        List<JanusConfig> failedConfigs = com.google.common.collect.Lists.newArrayList();
        for (int i=0;i<configInfos.size();i++) {
            try {
                String configJson = JsonUtil.toJson(configInfos.get(i));
                StringEntity entity = new StringEntity(configJson, "utf-8");
                put.setEntity(entity);
                httpResponse = httpClient.execute(put);
                respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                Response resp = JsonUtil.toBean(respStr, Response.class);
                if (resp.getCode() != 0) {
                    failedConfigs.add(configInfos.get(i));
                }
                httpResponse.getEntity().getContent().close();
            } catch (Exception e) {
                failedConfigs.add(configInfos.get(i));
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("失败" + failedConfigs.size() + "--------------------");
        for (JanusConfig config : failedConfigs) {
            System.out.println(config.getBaseConfig().getUri());
        }

    }
}
