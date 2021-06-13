package program.tiger.sword.http;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.StringUtils;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author junhu.li
 * @ClassName SyncLeonidStageConfig
 * @Description TODO
 * @date 2019-08-1214:49
 * @Version 1.0.0
 */
public class SyncLeonidStageConfig {


    private static final String groupId = "5d4ac8d39725eb0007682790";
    private static final String leonidToken = "5c32fcbfa753370007ad4065";
    private static final String assetKey = "5b3dd9f5a753370007f387f4";
    private static final String leonidGroupId = "5722d47cafb33f0e0068934c";
    private static final String janusToken = "5d07036b34572400072b4a70";
    private static final String janusHost = "http://janus.t.17usoft.com";


    private static Function<LeonidConfig.Rewrite, JanusConfig.Rewrite> rewriteRewriteFunction = leonidRewrite -> {
        JanusConfig.Rewrite janusRewrite = new JanusConfig.Rewrite();
        janusRewrite.setCond(leonidRewrite.getCond());
        janusRewrite.setEnd(leonidRewrite.getEnd());
        janusRewrite.setStart(leonidRewrite.getStart());
        janusRewrite.setType(leonidRewrite.getType());
        return janusRewrite;
    };


    public static void main(String[] args) throws Exception {

        HttpResponse httpResponse;
        String respStr;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        /*HttpGet appServerGet = new HttpGet("http://leonidtq.t.17usoft.com/libraapi2/leonid/v2/appserver/server?serverId=-1&nodeGroupId=" + leonidGroupId + "&pagenum=1&pagesize=150");
        appServerGet.addHeader("user-token", leonidToken);
        appServerGet.addHeader("asset-key", assetKey);
        HttpResponse httpResponse = httpClient.execute(appServerGet);
        String respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        LeonidAppServer.Response appServerResponse = JsonUtil.toBean(respStr, LeonidAppServer.Response.class);

        HttpGet nameServerGet = new HttpGet("http://leonidtq.t.17usoft.com/libraapi2/leonid/v2/nameServer/list?datesort=-1&nodeGroupId=" + leonidGroupId + "&pagenum=1&pagesize=15");
        nameServerGet.addHeader("user-token", leonidToken);
        nameServerGet.addHeader("asset-key", assetKey);
        httpResponse = httpClient.execute(nameServerGet);
        respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        LeonidNameServer.Response nameServerResponse = JsonUtil.toBean(respStr, LeonidNameServer.Response.class);


        Map<String, LeonidAppServer> appServerMap = Maps.uniqueIndex(appServerResponse.getResult(), leonidAppServer ->
                {
                    assert leonidAppServer != null;
                    return leonidAppServer.getId();
                }
        );
        final Map<String, LeonidNameServer> nameServerMap = Maps.uniqueIndex(nameServerResponse.getResult(), LeonidNameServer::getId
        );

        //查询janus appServer 信息
        HttpGet janusAppServerGet = new HttpGet(janusHost + "/janus-api/api/server/" + groupId + "/list?id=&name=&pageSize=200&pageNum=1&nameServer=");
        janusAppServerGet.setHeader("user-token", janusToken);
        httpResponse = httpClient.execute(janusAppServerGet);
        respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JanusAppServer.AppServerResponse janusAppServerResponse = JsonUtil.toBean(respStr, JanusAppServer.AppServerResponse.class);

        final Map<String, JanusAppServer> janusAppServerMap = Maps.uniqueIndex(janusAppServerResponse.getResult().getPageData(), janusAppServer ->
                {
                    assert janusAppServer != null;
                    return janusAppServer.getName();
                }
        );*/
        HttpGet leonidConfigGet = new HttpGet("http://leonidtq.t.17usoft.com/libraapi2/leonid/v2/uriconfig/config/all?configId=-1&nodeGroupId=" + leonidGroupId + "&upstreamName=ssz&pagenum=1&pagesize=800");
        leonidConfigGet.addHeader("user-token", leonidToken);
        leonidConfigGet.addHeader("asset-key", assetKey);
        httpResponse = httpClient.execute(leonidConfigGet);
        respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        LeonidConfig.LeonidConfigResponse leonidConfigResponse = JsonUtil.toBean(respStr, LeonidConfig.LeonidConfigResponse.class);


        List<JanusConfig> janusConfigs = leonidConfigResponse.getResult().getUriConfigInfo().stream().map(leonidConfig -> {
/*            LeonidAppServer leonidAppServer = appServerMap.get(leonidConfig.getServerGroupId());
            LeonidNameServer leonidNameServer = nameServerMap.get(leonidConfig.getServerGroupId());
            if (leonidAppServer == null && leonidNameServer == null) {
                return null;
            }

            String janusAppServerIdKey = leonidAppServer == null ? leonidNameServer.getName() : leonidAppServer.getName();
            if (StringUtils.isEmpty(janusAppServerIdKey) || janusAppServerMap.get(janusAppServerIdKey) == null) {
                return null;
            }*/
            JanusConfig.BaseConfig baseConfig = new JanusConfig.BaseConfig();
            JanusConfig.DefaultUpstream defaultUpstream = new JanusConfig.DefaultUpstream();
            //baseConfig.setServerId(janusAppServerMap.get(janusAppServerIdKey).getId());
            defaultUpstream.setServerId("5d8b21f0989996860367e556");
            JanusConfig janusConfig = new JanusConfig();
            baseConfig.setGroupId(groupId);
            baseConfig.setHosts(Lists.newArrayList(leonidConfig.getHost()));
            defaultUpstream.setRewrites(leonidConfig.getRewrites().stream().filter(leonidRewrite -> leonidRewrite.getEnd().length() > 0 || leonidRewrite.getStart().length() > 0).map(rewriteRewriteFunction).collect(Collectors.toList()));
            if (StringUtils.isEmpty(leonidConfig.getNextUpstreamRules())) {
                leonidConfig.setNextUpstreamRules("error timeout");
            }
            baseConfig.setNextUpstreamRules(Splitter.on(' ').splitToList(leonidConfig.getNextUpstreamRules()));
            baseConfig.setNextUpstreamTimeout(StringUtils.isEmpty(leonidConfig.getNextUpstreamTimeOut()) ? 0 : Integer.parseInt(leonidConfig.getNextUpstreamTimeOut()));
            baseConfig.setNextUpstreamTries(StringUtils.isEmpty(leonidConfig.getNextUpstreamTries()) ? 0 : Integer.parseInt(leonidConfig.getNextUpstreamTries()));
            baseConfig.setUsing(leonidConfig.isEnabled());
            defaultUpstream.setNameServerEnable(leonidConfig.isNameServerEnable());
            defaultUpstream.setProxyPassType("server");
            baseConfig.setUri(leonidConfig.getUri());
            baseConfig.setDefaultUpstream(defaultUpstream);
            janusConfig.setBaseConfig(baseConfig);
            return janusConfig;
        }).collect(Collectors.toList());

        HttpPost post = new HttpPost(janusHost + "/janus-api/api/uriconfig/" + groupId + "/config");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("user-token", janusToken);
        List<JanusConfig> failedConfigs = Lists.newArrayList();
        janusConfigs.stream().filter(Objects::nonNull).collect(Collectors.toList()).forEach(janusConfig -> {
            try {
                String configJson = JsonUtil.toJson(janusConfig);
                StringEntity entity = new StringEntity(configJson, "utf-8");
                post.setEntity(entity);
                HttpResponse janusHttpResponse = httpClient.execute(post);
                String janusRespStr = new BufferedReader(new InputStreamReader(janusHttpResponse.getEntity().getContent()))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                Response resp = JsonUtil.toBean(janusRespStr, Response.class);
                if (resp.getCode() != 0) {
                    failedConfigs.add(janusConfig);
                }
                janusHttpResponse.getEntity().getContent().close();
            } catch (Exception e) {
                failedConfigs.add(janusConfig);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("同步配置信息失败" + failedConfigs.size() + "--------------------");
        for (JanusConfig config : failedConfigs) {
            System.out.println(config.getBaseConfig().getUri());
        }


    }

}
