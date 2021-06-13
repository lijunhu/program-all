package program.tiger.sword.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import program.tiger.sword.common.response.JsonDateSerializer;
import program.tiger.sword.common.response.LeonidResponse;

import java.util.Date;
import java.util.List;

/**
 * @author junhu.li
 * @ClassName LeonidAppServer
 * @Description TODO
 * @date 2019-08-1210:48
 * @Version 1.0.0
 */
@Getter
@Setter
public class LeonidAppServer {

    @JsonProperty("_id")
    private String id;
    private String name;
    private String checkUrl;
    private boolean checkUrlEnable;
    private String host;
    private String describe;
    private String opUpstream;
    private String appUk;
    private boolean enabled;
    private String nodeGroupId;
    @JsonProperty("isDynamicLoad")
    private boolean dynamicLoad;
    private String balanceLevel;
    private Integer standardTime;
    private List<SubAppServer> members;
    @JsonProperty("isSessionStick")
    private boolean sessionStick;
    @JsonProperty("isIpHash")
    private boolean ipHash;
    private boolean sagittariusEnabled;
    private boolean aquariusEnabled;
    private String groupEname;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date healthCheckTime;
    private String errIp;
    private String projId;
    private String createUserId;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date updateTime;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date writeTime;
    @JsonProperty("asset_key")
    private String assetKey;

    @Getter
    @Setter
    public static class SubAppServer {
        private String ip;
        private Integer port;
        private Integer weight;
        private boolean enabled;
        @JsonProperty("isTest")
        private boolean test;
        @JsonProperty("isBackUp")
        private boolean backUp;
        private String swim;
        private String swimName;
        private Integer maxFails;
        private Integer failTimeOut;
    }


    @Getter
    @Setter
    public static class Response extends LeonidResponse<List<LeonidAppServer>> {
        private List<LeonidAppServer> result;
    }
}
