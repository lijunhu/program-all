package program.tiger.sword.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import program.tiger.sword.common.response.JsonDateSerializer;

import java.util.Date;
import java.util.List;

/**
 * @author junhu.li
 * @ClassName JanusAppServer
 * @Description TODO
 * @date 2019-08-1214:57
 * @Version 1.0.0
 */
@Getter
@Setter
public class JanusAppServer {

    private String id;
    private String name;
    private String checkUrl;
    private boolean checkUrlEnable;
    private String host;
    private String describe;
    private boolean enabled;
    private String groupId;
    private List<SubJanusAppServer> members;
    @JsonProperty("isSessionStick")
    private boolean sessionStick;
    @JsonProperty("isIpHash")
    private boolean ipHash;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date createTime;
    private String createUserId;
    private String createUserName;
    private boolean useNameServer;
    private String nameServer;
    private List<String> nameEnvList;
    private String nameEnv;
    private String defaultNameEnv;
    private Date updateTime;
    private String updateUserId;
    private String updateUserName;


    @Getter
    @Setter
    public static class SubJanusAppServer {
        private String ip;
        private Integer port;
        private Integer weight;
        private boolean enabled;
        private Integer maxFails;
        private Integer failTimeOut;
    }


    @Setter
    @Getter
    public static class AppServerResponse extends Response{
        private PageAppServerResponse result;
    }

    @Getter
    @Setter
    public static class PageAppServerResponse extends Response.PageResponse {
        private List<JanusAppServer> pageData;
        private int totalCount;
    }
}
