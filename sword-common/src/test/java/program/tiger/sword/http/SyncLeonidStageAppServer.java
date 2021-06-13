package program.tiger.sword.http;

import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author junhu.li
 * @ClassName SyncLeonidStageAppServer
 * @Description TODO
 * @date 2019-08-1210:47
 * @Version 1.0.0
 */
public class SyncLeonidStageAppServer {
    private static final String groupId = "5d4fd6889725eb00076744c4";
    private static final String leonidToken = "5c32fcbfa753370007ad4065";
    private static final String assetKey = "5b3dd9f5a753370007f387f4";
    private static final String janusToken = "5d07036b34572400072b4a70";
    private static final String janusHost = "http://janus.t.17usoft.com";


    private static Function<LeonidAppServer.SubAppServer, JanusAppServer.SubJanusAppServer> subAppServerSubJanusAppServerFunction = subAppServer -> {
        JanusAppServer.SubJanusAppServer subJanusAppServer = new JanusAppServer.SubJanusAppServer();
        assert subAppServer != null;
        subJanusAppServer.setEnabled(subAppServer.isEnabled());
        subJanusAppServer.setIp(subAppServer.getIp());
        subJanusAppServer.setPort(subAppServer.getPort());
        subJanusAppServer.setFailTimeOut(subAppServer.getFailTimeOut());
        subJanusAppServer.setMaxFails(subAppServer.getMaxFails());
        subJanusAppServer.setWeight(subAppServer.getWeight());
        return subJanusAppServer;
    };

    private static Function<LeonidAppServer, JanusAppServer> janusAppServerLeonidAppServerFunction = leonidAppServer -> {
        JanusAppServer janusAppServer = new JanusAppServer();
        assert leonidAppServer != null;
        janusAppServer.setName(leonidAppServer.getName());
        janusAppServer.setCheckUrl(leonidAppServer.getCheckUrl());
        janusAppServer.setCheckUrlEnable(leonidAppServer.isCheckUrlEnable());
        janusAppServer.setHost(leonidAppServer.getHost());
        janusAppServer.setDescribe(leonidAppServer.getDescribe());
        janusAppServer.setMembers(leonidAppServer.getMembers().stream().map(subAppServerSubJanusAppServerFunction).collect(Collectors.toList()));
        janusAppServer.setGroupId(groupId);
        janusAppServer.setEnabled(leonidAppServer.isEnabled());
        janusAppServer.setIpHash(leonidAppServer.isIpHash());
        janusAppServer.setSessionStick(leonidAppServer.isSessionStick());
        janusAppServer.setUseNameServer(Boolean.FALSE);
        return janusAppServer;
    };

    private static Function<LeonidNameServer, JanusAppServer> leonidNameServerJanusAppServerFunction = leonidNameServer -> {
        JanusAppServer janusAppServer = new JanusAppServer();
        assert leonidNameServer != null;
        janusAppServer.setEnabled(leonidNameServer.isEnabled());
        janusAppServer.setName(leonidNameServer.getName());
        janusAppServer.setUseNameServer(true);
        janusAppServer.setCheckUrl(leonidNameServer.getCheckUrl());
        janusAppServer.setCheckUrlEnable(leonidNameServer.isCheckUrlEnable());
        janusAppServer.setDescribe(leonidNameServer.getDescribe());
        janusAppServer.setHost(leonidNameServer.getHost());
        janusAppServer.setNameEnvList(leonidNameServer.getEnv());
        janusAppServer.setDefaultNameEnv(leonidNameServer.getEnv().get(0));
        janusAppServer.setSessionStick(leonidNameServer.isSessionStick());
        janusAppServer.setIpHash(leonidNameServer.isIpHash());
        janusAppServer.setGroupId(groupId);
        janusAppServer.setNameServer(leonidNameServer.getName());
        janusAppServer.setUseNameServer(true);
        return janusAppServer;
    };


    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet appServerGet = new HttpGet("http://leonidtq.t.17usoft.com/libraapi2/leonid/v2/appserver/server?serverId=-1&nodeGroupId=572961594be2530d00ed9a1e&pagenum=1&pagesize=150");
        appServerGet.addHeader("user-token", leonidToken);
        appServerGet.addHeader("asset-key", assetKey);
        HttpResponse httpResponse = httpClient.execute(appServerGet);
        String respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        LeonidAppServer.Response appServerResponse = JsonUtil.toBean(respStr, LeonidAppServer.Response.class);

        HttpGet nameServerGet = new HttpGet("http://leonidtq.t.17usoft.com/libraapi2/leonid/v2/nameServer/list?datesort=-1&nodeGroupId=572961594be2530d00ed9a1e&pagenum=1&pagesize=15");
        nameServerGet.addHeader("user-token", leonidToken);
        nameServerGet.addHeader("asset-key", assetKey);
        httpResponse = httpClient.execute(nameServerGet);
        respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        LeonidNameServer.Response nameServerResponse = JsonUtil.toBean(respStr, LeonidNameServer.Response.class);

        List<JanusAppServer> janusAppServers = appServerResponse.getResult().stream().map(janusAppServerLeonidAppServerFunction).collect(Collectors.toList());
        janusAppServers.addAll(nameServerResponse.getResult().stream().map(leonidNameServerJanusAppServerFunction).collect(Collectors.toList()));


        HttpPost post = new HttpPost(janusHost + "/janus-api/api/server/" + groupId);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("user-token", janusToken);
        List<JanusAppServer> failedAppServer = Lists.newArrayList();
        for (JanusAppServer janusAppServer : janusAppServers) {
            try {
                String appServerJson = JsonUtil.toJson(janusAppServer);
                StringEntity entity = new StringEntity(appServerJson, "utf-8");
                post.setEntity(entity);
                httpResponse = httpClient.execute(post);
                respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                Response resp = JsonUtil.toBean(respStr, Response.class);
                if (resp.getCode() != 0) {
                    failedAppServer.add(janusAppServer);
                }
                httpResponse.getEntity().getContent().close();
            } catch (Exception e) {
                failedAppServer.add(janusAppServer);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("同步服务器----失败" + failedAppServer.size() + "--------------------");
        for (JanusAppServer janusAppServer : failedAppServer) {
            System.out.println(janusAppServer.getName());
        }
    }


}
