package program.tiger.sword.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import program.tiger.sword.common.response.JsonDateSerializer;
import program.tiger.sword.common.response.LeonidResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author junhu.li
 * @ClassName LeonidConfig
 * @Description TODO
 * @date 2019-08-1214:23
 * @Version 1.0.0
 */
@Getter
@Setter
public class LeonidConfig {
    @JsonProperty("_id")
    private String id;
    private String uri;
    private List<String> tag;
    @JsonProperty("rewrite2")
    private List<Rewrite> rewrites;
    private String host;
    @JsonProperty("isNameServer")
    private boolean nameServerEnable;
    private String serverGroupId;
    private String nodeGroupId;
    private String projId;
    private Map<String,String> cusResHeader;
    private boolean enabled;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date updateTime;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date writeTime;
    private String nextUpstreamRules;
    private String nextUpstreamTimeOut;
    private String nextUpstreamTries;


    //缓存配置
    private boolean pageCache;
    private Integer pageCacheLocalMaxAge;
    private String pageCacheContentType;
    private List<String> pageCacheKeyParamList;
    private List<String> pageCacheKeyCookieList;
    private String pageCacheNeedContentStr;
    private String pageCacheStaticBackup;
    private boolean pageCacheDiffDeveice;
    private boolean pageCacheDiffAddr;
    private boolean pageCacheParseTplEnabled;
    private boolean pageCacheIsNotCacheAjax;
    private boolean pageCacheIsHA;
    private boolean pageCacheIsSpider;


    @Getter
    @Setter
    public static class Rewrite{
        private String cond;
        private String type;
        private String start;
        private String end;
    }
    @Getter
    @Setter
    public static class LeonidConfigResponse extends LeonidResponse<ConfigInfos> {
        private ConfigInfos result;
    }

    @Setter
    @Getter
    public static class ConfigInfos{
        private List<LeonidConfig> uriConfigInfo;
        private String configVersion;
    }
}
