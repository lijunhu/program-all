package program.tiger.sword.http;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author junhu.li
 * @ClassName UpdateSzConfig
 * @Description TODO
 * @date 2019-08-1910:34
 * @Version 1.0.0
 */
public class UpdateSzConfig {
    private static final String groupId = "5d4fd6889725eb00076744c4";
    //private static final String groupId = "5d512ac9b1e3c108063e7118";    //测试环境


    private static final String janusToken = "5d07036b34572400072b4a70";
    //private static final String janusToken = "5cfb2d9bb1e3c1519246ce24"; //测试环境


    private static final String janusHost = "http://janus.t.17usoft.com";
    //private static final String janusHost = "http://10.100.202.119:6010";//测试环境

    private static final String leonidToken = "5c32fcbfa753370007ad4065";
    private static final String assetKey = "5b3dd9f5a753370007f387f4";


    private static final Joiner underLineJoiner = Joiner.on("_");

    public static void main(String[] args) throws Exception {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet leonidConfigGet = new HttpGet("http://leonidtq.t.17usoft.com/libraapi2/leonid/v2/uriconfig/config/all?configId=-1&nodeGroupId=572961594be2530d00ed9a1e&pagenum=1&pagesize=800");
        leonidConfigGet.addHeader("user-token", leonidToken);
        leonidConfigGet.addHeader("asset-key", assetKey);
        HttpResponse httpResponse = httpClient.execute(leonidConfigGet);
        String respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        LeonidConfig.LeonidConfigResponse leonidConfigResponse = JsonUtil.toBean(respStr, LeonidConfig.LeonidConfigResponse.class);


        HttpGet janusConfigGet = new HttpGet(janusHost + "/janus-api/api/uriconfig/" + groupId + "/config/list?pageSize=500");
        janusConfigGet.addHeader("user-token", janusToken);
        httpResponse = httpClient.execute(janusConfigGet);
        respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JanusConfig.ConfigResponse configResponse = JsonUtil.toBean(respStr, JanusConfig.ConfigResponse.class);
        List<JanusConfig> janusConfigInfos = configResponse.result.pageData;
        Map<String, JanusConfig> janusConfigInfoMap = Maps.uniqueIndex(janusConfigInfos, janusConfigInfo -> underLineJoiner.join(janusConfigInfo.getBaseConfig().getUri(), underLineJoiner.join(janusConfigInfo.getBaseConfig().getHosts())));

        List<JanusConfig> szJanusConfigs = Lists.newArrayList();
        for (LeonidConfig leonidConfig : leonidConfigResponse.getResult().getUriConfigInfo()) {
            JanusConfig janusConfig = janusConfigInfoMap.get(underLineJoiner.join(leonidConfig.getUri(), leonidConfig.getHost()));
            if (janusConfig != null) {
                JanusConfig.PageCache pageCache = new JanusConfig.PageCache();
                pageCache.setUsing(leonidConfig.isPageCache());
                pageCache.setDiffAddr(leonidConfig.isPageCacheDiffAddr());
                pageCache.setDiffDeveice(leonidConfig.isPageCacheDiffDeveice());
                pageCache.setDynamicBackup(leonidConfig.isPageCacheIsHA());
                pageCache.setLocalMaxAge(leonidConfig.getPageCacheLocalMaxAge());
                pageCache.setContentType(leonidConfig.getPageCacheContentType());
                pageCache.setNeedContentStr(leonidConfig.getPageCacheNeedContentStr());
                pageCache.setParseTemplate(leonidConfig.isPageCacheParseTplEnabled());
                pageCache.setParamKeyList(leonidConfig.getPageCacheKeyParamList());
                pageCache.setCookieKeyList(leonidConfig.getPageCacheKeyCookieList());
                pageCache.setSpider(leonidConfig.isPageCacheIsSpider());
                pageCache.setStaticBackup(leonidConfig.getPageCacheStaticBackup());
                pageCache.setCacheAjax(!leonidConfig.isPageCacheIsNotCacheAjax());
                janusConfig.setPageCache(pageCache);
                szJanusConfigs.add(janusConfig);
            }

        }
        System.out.println(szJanusConfigs.size());

        HttpPut put = new HttpPut(janusHost + "/janus-api/api/uriconfig/" + groupId + "/config");
        put.addHeader("Content-Type", "application/json");
        put.addHeader("user-token", janusToken);
        List<JanusConfig> failedConfigs = Lists.newArrayList();
        for (int i = 0; i < szJanusConfigs.size(); i++) {
            try {
                String configJson = JsonUtil.toJson(szJanusConfigs.get(i));
                StringEntity entity = new StringEntity(configJson, "utf-8");
                put.setEntity(entity);
                httpResponse = httpClient.execute(put);
                respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                Response resp = JsonUtil.toBean(respStr, Response.class);
                if (resp.getCode() != 0) {
                    failedConfigs.add(szJanusConfigs.get(i));
                }
                httpResponse.getEntity().getContent().close();
            } catch (Exception e) {
                failedConfigs.add(szJanusConfigs.get(i));
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
