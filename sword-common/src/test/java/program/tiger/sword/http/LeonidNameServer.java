package program.tiger.sword.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import program.tiger.sword.common.response.LeonidResponse;

import java.util.Date;
import java.util.List;

/**
 * @author junhu.li
 * @ClassName LeonidNameServer
 * @Description TODO
 * @date 2019-08-1215:25
 * @Version 1.0.0
 */
@Getter
@Setter
public class LeonidNameServer {
    @JsonProperty("_id")
    private String id;
    private String name;
    private List<String> env;
    private String checkUrl;
    private String host;
    private boolean checkUrlEnable;
    private String describe;
    private boolean enabled;
    private String nodeGroupId;
    @JsonProperty("isSessionStick")
    private boolean sessionStick;
    @JsonProperty("isIpHash")
    private boolean ipHash;
    private String createUserId;
    private Date updateTime;
    private Date writeTime;
    private String assetKey;

    @Getter
    @Setter
    public static class Response extends LeonidResponse<List<LeonidNameServer>> {
        private List<LeonidNameServer> result;
    }
}
