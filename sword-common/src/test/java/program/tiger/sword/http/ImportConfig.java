package program.tiger.sword.http;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.checkerframework.checker.nullness.qual.Nullable;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author junhu.li
 * @ClassName ImportConfig
 * @Description TODO
 * @date 2019-07-1910:38
 * @Version 1.0.0
 */
public class ImportConfig {


    public static void main(String[] args) throws Exception{

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet configGet = new HttpGet("http://janus.t.17usoft.com/janus-api/api/uriconfig/5d10bdb89725eb00077bace6/config/list?uri=mtools&pageSize=200");
        configGet.addHeader("user-token", "5d07036b34572400072b4a70");
        HttpResponse httpResponse = httpClient.execute(configGet);
        String respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JanusConfig.ConfigResponse configResponse = JsonUtil.toBean(respStr,JanusConfig.ConfigResponse.class);
        List<JanusConfig> configInfos = configResponse.result.pageData;


        HttpGet existConfigGet = new HttpGet("http://janus.17usoft.com/janus-api/api/uriconfig/5d10bdb89725eb00077bace6/config/list?uri=mtools&pageSize=200");
        existConfigGet.addHeader("user-token", "5d07036b34572400072b4a70");
        httpResponse = httpClient.execute(existConfigGet);
        respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JanusConfig.ConfigResponse existConfigResponse = JsonUtil.toBean(respStr,JanusConfig.ConfigResponse.class);
        List<JanusConfig> existConfigInfos = existConfigResponse.result.pageData;
        Map<String,JanusConfig> existConfigInfoMap = Maps.uniqueIndex(existConfigInfos, new Function<JanusConfig, String>() {
            @Nullable
            @Override
            public String apply(JanusConfig input) {
                return input.getBaseConfig().getUri();
            }
        });



/*        HttpGet templateGet = new HttpGet("http://janus.17usoft.com/janus-api/api/template/5d10bdb89725eb00077bace6/list?templateId=5d22a5bf9725eb0007b5e984");
        templateGet.addHeader("user-token", "5d07036b34572400072b4a70");
        httpResponse = httpClient.execute(templateGet);
        respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));

        JanusTemplate.TemplateResponse  templateResponse = JsonUtil.toBean(respStr,JanusTemplate.TemplateResponse.class);
        JanusTemplate janusTemplate = templateResponse.getResult().getPageData().getTemplateInfos().get(0);*/

        ListIterator<JanusConfig> iterator = configInfos.listIterator();
        while(iterator.hasNext()){

            JanusConfig configInfo = iterator.next();

/*            configInfo.setIpLocation(janusTemplate.getIpLocation());
            configInfo.setPageCache(janusTemplate.getPageCache());
            configInfo.setCanary(janusTemplate.getCanary());
            configInfo.setValidateRequest(janusTemplate.getValidateRequest());
            configInfo.setTransProtocol(janusTemplate.getTransProtocol());
            configInfo.setCompress(janusTemplate.getCompress());
            configInfo.setDecompress(janusTemplate.getDecompress());
            configInfo.setCrypt(janusTemplate.getCrypt());
            configInfo.setDecrypt(janusTemplate.getDecrypt());
            configInfo.setAntiSpider(janusTemplate.getAntiSpider());
            configInfo.setIntercept(janusTemplate.getIntercept());


            configInfo.getBaseConfig().setUsing(false);
            configInfo.getBaseConfig().setServerId("5d10c0e0989996860365f27b");
            configInfo.getBaseConfig().setNameServer(true);
            configInfo.getBaseConfig().setGroupId("5d10bdb89725eb00077bace6");
            configInfo.getBaseConfig().setTemplateId(janusTemplate.getId());
            configInfo.getBaseConfig().setTemplateName(janusTemplate.getTemplateName());
            configInfo.getBaseConfig().setHosts(Lists.newArrayList("mobile-api2011.elong.com","mobile-api2017.elong.com","tcmapi.vip.elong.com","tcmapi.elong.com","mobile-api.elong.com","10.152.8.46"));
            configInfo.setCreateUserId(null);
            configInfo.setCreateUserName(null);
            configInfo.setCreateTime(null);
            configInfo.setUpdateUserId(null);
            configInfo.setUpdateUserName(null);
            configInfo.setUpdateTime(null);*/

            configInfo.getCheckList().setUsing(true);
            configInfo.getCheckList().setProductLine("mtools");

            configInfo.getException().setUsing(true);
            configInfo.getException().setModel("elong");
        }

        System.out.println(JsonUtil.toJson(configInfos));
        System.out.println(configInfos.size());
        HttpPut put = new HttpPut("http://janus.17usoft.com/janus-api/api/uriconfig/5d10bdb89725eb00077bace6/config");
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

        //HttpPost post = new HttpPost("http://10.100.202.119:6010/janus-api/api/uriconfig/5cfdc23bb1e3c14401608cfb/config");

    }
}
