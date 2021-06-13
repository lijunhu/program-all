package program.tiger.sword.http;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import program.tiger.sword.common.ThrowingConsumerWrapper;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author junhu.li
 * @ClassName ExportMvcProj
 * @Description TODO
 * @date 2020/3/3114:56
 * @Version 1.0.0
 */
public class ExportMvcProj {

    @Data
    public static class MvcUserInfo implements Cloneable {
        private String configId;
        private String projId;
        private List<String> users;
        private String projName;
        private String serverId;
        private String serverName;
        private List<String> hosts;
        private String uri;

        @Override
        public MvcUserInfo clone() throws CloneNotSupportedException {
            MvcUserInfo clone = (MvcUserInfo) super.clone();
            MvcUserInfo userInfo = new MvcUserInfo();
            userInfo.setUri(clone.getUri());
            userInfo.setHosts(clone.getHosts());
            userInfo.setServerName(clone.getServerName());
            userInfo.setServerId(clone.serverId);
            userInfo.setUsers(clone.getUsers());
            userInfo.setProjName(clone.getProjName());
            userInfo.setProjId(clone.getProjId());
            userInfo.setConfigId(clone.getConfigId());
            return userInfo;
        }
    }

    private static final String leonidHost = "http://leonidtq.t.17usoft.com";
    private static final String projUri = "/libraapi2/mvc/v1/project/search";
    private static final String projUsersUri = "/libraapi2/mvc/v1/project/remotegit/users";
    private static final String userInfoUri = "/libraapi2/oreo/auth/user";


    private static final String userAssetKey = "5c32fcbfa753370007ad4067";
    private static final String userToken = "5c32fcbfa753370007ad4065";

    private static final Header userAssetKeyHeader = new BasicHeader("asset-key", userAssetKey);
    private static final Header userTokenHeader = new BasicHeader("user-token", userToken);


    private static final String janusUserToken = "5d07036b34572400072b4a70";
    private static final String janusHost = "http://janus.17usoft.com";
    private static final String janusConfigUri = "/janus-api/api/uriconfig/5d4ac8d39725eb0007682790/config/list";
    private static final String janusConfigDetailUri = "/janus-api/api/uriconfig/5d4ac8d39725eb0007682790/config/detail";

    private static ExecutorService pool;

    static {
        pool = new ThreadPoolExecutor(4, 8, 200L, TimeUnit.MINUTES,
                new SynchronousQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    }


    public static void main(String[] args) throws Exception {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet projGet = new HttpGet(leonidHost + projUri + "?" + "language_type=node");
        projGet.setHeader(userAssetKeyHeader);
        projGet.setHeader(userTokenHeader);
        HttpResponse response = httpClient.execute(projGet);
        String respStr = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));

        MvcProj.Response mvcResponse = JsonUtil.toBean(respStr, MvcProj.Response.class);


        HttpGet userInfoGet = new HttpGet(leonidHost + userInfoUri);
        userInfoGet.setHeader(userAssetKeyHeader);
        userInfoGet.setHeader(userTokenHeader);
        response = httpClient.execute(userInfoGet);
        respStr = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        LeonidUser.Response userResponse = JsonUtil.toBean(respStr, LeonidUser.Response.class);

        List<LeonidUser> users = JsonUtil.toList(userResponse.getResult(), LeonidUser.class);
        Map<String, String> userNameMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(users)) {
            for (LeonidUser user : users) {
                userNameMap.put(user.getUserId(), user.getName());
            }
        }

        List<MvcUserInfo> userInfoList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(mvcResponse.getResult())) {
            for (MvcProj proj : mvcResponse.getResult()) {

                MvcUserInfo userInfo = new MvcUserInfo();
                userInfo.setProjId(proj.getId());
                userInfo.setProjName(proj.getName());

                HttpGet projUserGet = new HttpGet(leonidHost + projUsersUri + "?" + "pid=" + proj.getId());
                projUserGet.setHeader(userTokenHeader);
                projUserGet.setHeader("asset-key", proj.getAssetKey());
                response = httpClient.execute(projUserGet);
                respStr = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));

                ProjUser.Response projUserResponse = JsonUtil.toBean(respStr, ProjUser.Response.class);

                List<String> userIdNameList = Lists.newArrayList();
                if (CollectionUtils.isNotEmpty(projUserResponse.getResult())) {

                    for (ProjUser user : projUserResponse.getResult()) {
                        userIdNameList.add(String.format("%s(%s)", userNameMap.get(user.getUser()), user.getUser()));
                    }
                }
                userInfo.setUsers(userIdNameList);
                userInfoList.add(userInfo);
            }
        }


        List<List<JanusConfig.BaseConfig>> configsList = Lists.partition(queryJanusConfig(), 100);

        List<MvcUserInfo> result = Lists.newCopyOnWriteArrayList();
        final CountDownLatch countDown = new CountDownLatch(configsList.size());
        configsList.forEach(configs ->
                pool.submit(
                        () -> {
                            configs.forEach(config ->
                                    config.getDefaultUpstream().getRewrites().forEach(rewrite -> {

                                        userInfoList.forEach(userInfo -> Optional.ofNullable(userInfo).ifPresent(
                                                ThrowingConsumerWrapper.handlingConsumerWrapper(user -> {
                                                    if (StringUtils.contains(rewrite.getEnd(), user.getProjId())) {

                                                        MvcUserInfo newUserInfo = user.clone();

                                                        newUserInfo.setConfigId(config.getId());
                                                        newUserInfo.setHosts(config.getHosts());
                                                        newUserInfo.setUri(config.getUri());
                                                        newUserInfo.setServerId(config.getDefaultUpstream().getServerId());
                                                        newUserInfo.setServerName(config.getDefaultUpstream().getUpstreamName());
                                                        result.add(newUserInfo);
                                                    }
                                                }, Exception.class)
                                        ));
                                    })
                            );
                            countDown.countDown();
                        }
                ));
        countDown.await();


        for (MvcUserInfo userInfo : result) {
            System.out.println(userInfo.projName + "\t" + userInfo.getProjId() + "\t" + userInfo.getConfigId() + "\t" + Joiner.on(" ").join(userInfo.getHosts()) + "\t" + userInfo.getUri() + "\t"
                    + userInfo.getServerName() + "(" + userInfo.getServerId() + ")" + "\t" + Joiner.on(" ").join(userInfo.users));
        }
        System.out.println(result.size());
        pool.shutdown();
    }


    public static List<JanusConfig.BaseConfig> queryJanusConfig() throws Exception {

        List<JanusConfig.BaseConfig> resultConfigs = Lists.newArrayList();
        final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet configGet = new HttpGet(janusHost + janusConfigUri + "?" + "pageSize=1000&pageNum=1");
        configGet.setHeader("user-token", janusUserToken);
        HttpResponse response = httpClient.execute(configGet);
        String respStr = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JanusConfig.ConfigResponse configResp = JsonUtil.toBean(respStr, JanusConfig.ConfigResponse.class);


        Optional.of(configResp.getResult()).flatMap(pageResp -> Optional.ofNullable(pageResp.pageData)).ifPresent(janusConfigs ->
                janusConfigs.forEach(ThrowingConsumerWrapper.handlingConsumerWrapper(janusConfig -> {
                            HttpGet configDetailGet = new HttpGet(janusHost + janusConfigDetailUri + "?" + "routeId=" + janusConfig.getId());
                            configDetailGet.setHeader("user-token", janusUserToken);
                            HttpResponse configDetailResp = httpClient.execute(configDetailGet);
                            String configDetailRespStr = new BufferedReader(new InputStreamReader(configDetailResp.getEntity().getContent()))
                                    .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                            JanusConfig.ConfigDetailResponse configDetail = JsonUtil.toBean(configDetailRespStr, JanusConfig.ConfigDetailResponse.class);
                            configDetail.getResult().getBaseConfig().setId(janusConfig.getId());
                            resultConfigs.add(configDetail.getResult().getBaseConfig());
                        }
                        , Exception.class)
                ));

        return resultConfigs;

    }
}



