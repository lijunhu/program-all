package program.tiger.sword.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import program.tiger.sword.common.response.JsonDateSerializer;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author junhu.li
 * @ClassName JanusConfig
 * @Description TODO
 * @date 2019-06-1816:52
 * @Version 1.0.0
 */

public class JanusConfig {


    public static class Rewrite {
        private String cond;
        private String type;   //枚举last，break，redirect，permanent
        private String start; //rewrite前的匹配正则
        private String end;   //rewrite后的重写地址

        public String getCond() {
            return cond;
        }

        public void setCond(String cond) {
            this.cond = cond;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }
    }

    public static class PageCache {
        private boolean using;               //是否启用
        private int localMaxAge;     //本地缓存超时时间
        private String contentType;      //请求contentType
        private List<String> paramKeyList;    //页面关键参数key
        private List<String> cookieKeyList; //请求Cookie关键key
        private String needContentStr;//页面必须包含缓存内容
        private String staticBackup;    //静态兜底信息
        private boolean diffDeveice;      //是否区分设备 android、ios、pc
        private boolean diffAddr;          //是否区分地理位置
        private boolean parseTemplate;  //是否开启模板解析
        private boolean cacheAjax;         //是否缓存ajax请求
        private boolean dynamicBackup;  //是否开启动态兜底
        @JsonProperty("isSpider")
        private boolean isSpider;           //是否蜘蛛单独缓存

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public int getLocalMaxAge() {
            return localMaxAge;
        }

        public void setLocalMaxAge(int localMaxAge) {
            this.localMaxAge = localMaxAge;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public List<String> getParamKeyList() {
            return paramKeyList;
        }

        public void setParamKeyList(List<String> paramKeyList) {
            this.paramKeyList = paramKeyList;
        }

        public List<String> getCookieKeyList() {
            return cookieKeyList;
        }

        public void setCookieKeyList(List<String> cookieKeyList) {
            this.cookieKeyList = cookieKeyList;
        }

        public String getNeedContentStr() {
            return needContentStr;
        }

        public void setNeedContentStr(String needContentStr) {
            this.needContentStr = needContentStr;
        }

        public String getStaticBackup() {
            return staticBackup;
        }

        public void setStaticBackup(String staticBackup) {
            this.staticBackup = staticBackup;
        }

        public boolean isDiffDeveice() {
            return diffDeveice;
        }

        public void setDiffDeveice(boolean diffDeveice) {
            this.diffDeveice = diffDeveice;
        }

        public boolean isDiffAddr() {
            return diffAddr;
        }

        public void setDiffAddr(boolean diffAddr) {
            this.diffAddr = diffAddr;
        }

        public boolean isParseTemplate() {
            return parseTemplate;
        }

        public void setParseTemplate(boolean parseTemplate) {
            this.parseTemplate = parseTemplate;
        }

        public boolean isCacheAjax() {
            return cacheAjax;
        }

        public void setCacheAjax(boolean cacheAjax) {
            this.cacheAjax = cacheAjax;
        }

        public boolean isDynamicBackup() {
            return dynamicBackup;
        }

        public void setDynamicBackup(boolean dynamicBackup) {
            this.dynamicBackup = dynamicBackup;
        }

        public boolean isSpider() {
            return isSpider;
        }

        public void setSpider(boolean spider) {
            isSpider = spider;
        }
    }

    public static class IpLocation {
        private boolean using;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }
    }

    public static class BaseConfig implements Cloneable {
        private String id;
        private boolean using;
        private String templateId;
        private String templateName;
        private String uri;
        private List<String> hosts;
        private String groupId;
        private List<String> nextUpstreamRules;
        private int nextUpstreamTimeout;
        private int nextUpstreamTries;
        private List<String> tags;
        private boolean sslEnable;
        private boolean http2Enable;
        private String certificateId;
        private String certificateName;
        private String defaultNameEnv;
        private int readTimeOut;
        private int sendTimeOut;
        private int connectTimeOut;
        private DefaultUpstream defaultUpstream;


        @Override
        protected BaseConfig clone() throws CloneNotSupportedException {
            BaseConfig baseConfig = (BaseConfig) super.clone();
            /*baseConfig.using = using;
            baseConfig.templateId = templateId;
            baseConfig.templateName = templateName;
            baseConfig.uri = uri;
            baseConfig.hosts = hosts;
            baseConfig.groupId = groupId;
            baseConfig.nextUpstreamRules = nextUpstreamRules;
            baseConfig.nextUpstreamTimeout = nextUpstreamTimeout;
            baseConfig.nextUpstreamTries = nextUpstreamTries;
            baseConfig.tags = tags;
            baseConfig.defaultUpstream = defaultUpstream;
            baseConfig.sslEnable = sslEnable;
            baseConfig.http2Enable = http2Enable;
            baseConfig.certificateId = certificateId;
            baseConfig.certificateName = certificateName;
            baseConfig.proxyPassType = proxyPassType;*/
            return baseConfig;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public String getTemplateName() {
            return templateName;
        }

        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public List<String> getHosts() {
            return hosts;
        }

        public void setHosts(List<String> hosts) {
            this.hosts = hosts;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public List<String> getNextUpstreamRules() {
            return nextUpstreamRules;
        }

        public void setNextUpstreamRules(List<String> nextUpstreamRules) {
            this.nextUpstreamRules = nextUpstreamRules;
        }

        public int getNextUpstreamTimeout() {
            return nextUpstreamTimeout;
        }

        public void setNextUpstreamTimeout(int nextUpstreamTimeout) {
            this.nextUpstreamTimeout = nextUpstreamTimeout;
        }

        public int getNextUpstreamTries() {
            return nextUpstreamTries;
        }

        public void setNextUpstreamTries(int nextUpstreamTries) {
            this.nextUpstreamTries = nextUpstreamTries;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public boolean isSslEnable() {
            return sslEnable;
        }

        public void setSslEnable(boolean sslEnable) {
            this.sslEnable = sslEnable;
        }

        public boolean isHttp2Enable() {
            return http2Enable;
        }

        public void setHttp2Enable(boolean http2Enable) {
            this.http2Enable = http2Enable;
        }

        public String getCertificateId() {
            return certificateId;
        }

        public void setCertificateId(String certificateId) {
            this.certificateId = certificateId;
        }

        public String getCertificateName() {
            return certificateName;
        }

        public void setCertificateName(String certificateName) {
            this.certificateName = certificateName;
        }

        public String getDefaultNameEnv() {
            return defaultNameEnv;
        }

        public void setDefaultNameEnv(String defaultNameEnv) {
            this.defaultNameEnv = defaultNameEnv;
        }

        public int getReadTimeOut() {
            return readTimeOut;
        }

        public void setReadTimeOut(int readTimeOut) {
            this.readTimeOut = readTimeOut;
        }

        public int getSendTimeOut() {
            return sendTimeOut;
        }

        public void setSendTimeOut(int sendTimeOut) {
            this.sendTimeOut = sendTimeOut;
        }

        public int getConnectTimeOut() {
            return connectTimeOut;
        }

        public void setConnectTimeOut(int connectTimeOut) {
            this.connectTimeOut = connectTimeOut;
        }

        public DefaultUpstream getDefaultUpstream() {
            return defaultUpstream;
        }

        public void setDefaultUpstream(DefaultUpstream defaultUpstream) {
            this.defaultUpstream = defaultUpstream;
        }
    }


    private String id;
    private String locationId;
    private BaseConfig baseConfig;
    private IpLocation ipLocation;
    private PageCache pageCache;
    private Canary canary;
    private ValidateRequest validateRequest;
    private TransProtocol transProtocol;
    private Compress compress;
    private Decompress decompress;
    private Crypt crypt;
    private Decrypt decrypt;
    private AntiSpider antiSpider;
    private Intercept intercept;
    @JsonProperty("exception")
    private ExceptionModule exception;
    private CheckList checkList;
    private RequestValidateSandbox requestValidateSandbox;
    private SessionValidateSandbox sessionValidateSandbox;
    private String createUserId;
    private String createUserName;
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+08:00")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date createTime;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date updateTime;
    private String updateUserId;
    private String updateUserName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public BaseConfig getBaseConfig() {
        return baseConfig;
    }

    public void setBaseConfig(BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    public IpLocation getIpLocation() {
        return ipLocation;
    }

    public void setIpLocation(IpLocation ipLocation) {
        this.ipLocation = ipLocation;
    }

    public PageCache getPageCache() {
        return pageCache;
    }

    public void setPageCache(PageCache pageCache) {
        this.pageCache = pageCache;
    }

    public Canary getCanary() {
        return canary;
    }

    public void setCanary(Canary canary) {
        this.canary = canary;
    }

    public ValidateRequest getValidateRequest() {
        return validateRequest;
    }

    public void setValidateRequest(ValidateRequest validateRequest) {
        this.validateRequest = validateRequest;
    }

    public TransProtocol getTransProtocol() {
        return transProtocol;
    }

    public void setTransProtocol(TransProtocol transProtocol) {
        this.transProtocol = transProtocol;
    }

    public Compress getCompress() {
        return compress;
    }

    public void setCompress(Compress compress) {
        this.compress = compress;
    }

    public Decompress getDecompress() {
        return decompress;
    }

    public void setDecompress(Decompress decompress) {
        this.decompress = decompress;
    }

    public Crypt getCrypt() {
        return crypt;
    }

    public void setCrypt(Crypt crypt) {
        this.crypt = crypt;
    }

    public Decrypt getDecrypt() {
        return decrypt;
    }

    public void setDecrypt(Decrypt decrypt) {
        this.decrypt = decrypt;
    }

    public AntiSpider getAntiSpider() {
        return antiSpider;
    }

    public void setAntiSpider(AntiSpider antiSpider) {
        this.antiSpider = antiSpider;
    }

    public Intercept getIntercept() {
        return intercept;
    }

    public void setIntercept(Intercept intercept) {
        this.intercept = intercept;
    }

    public ExceptionModule getException() {
        return exception;
    }

    public void setException(ExceptionModule exception) {
        this.exception = exception;
    }

    public CheckList getCheckList() {
        return checkList;
    }

    public void setCheckList(CheckList checkList) {
        this.checkList = checkList;
    }

    public RequestValidateSandbox getRequestValidateSandbox() {
        return requestValidateSandbox;
    }

    public void setRequestValidateSandbox(RequestValidateSandbox requestValidateSandbox) {
        this.requestValidateSandbox = requestValidateSandbox;
    }

    public SessionValidateSandbox getSessionValidateSandbox() {
        return sessionValidateSandbox;
    }

    public void setSessionValidateSandbox(SessionValidateSandbox sessionValidateSandbox) {
        this.sessionValidateSandbox = sessionValidateSandbox;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public static class DefaultUpstream {
        private Map<String, String> customRequestHeaders;
        private String upstreamName;
        private String proxyPassType;
        private NeoTransform neoTransform;
        private String serverId;
        private List<Rewrite> rewrites;
        private boolean nameServerEnable;

        public Map<String, String> getCustomRequestHeaders() {
            return customRequestHeaders;
        }

        public void setCustomRequestHeaders(Map<String, String> customRequestHeaders) {
            this.customRequestHeaders = customRequestHeaders;
        }

        public String getUpstreamName() {
            return upstreamName;
        }

        public void setUpstreamName(String upstreamName) {
            this.upstreamName = upstreamName;
        }

        public String getProxyPassType() {
            return proxyPassType;
        }

        public void setProxyPassType(String proxyPassType) {
            this.proxyPassType = proxyPassType;
        }

        public NeoTransform getNeoTransform() {
            return neoTransform;
        }

        public void setNeoTransform(NeoTransform neoTransform) {
            this.neoTransform = neoTransform;
        }

        public String getServerId() {
            return serverId;
        }

        public void setServerId(String serverId) {
            this.serverId = serverId;
        }

        public List<Rewrite> getRewrites() {
            return rewrites;
        }

        public void setRewrites(List<Rewrite> rewrites) {
            this.rewrites = rewrites;
        }

        public boolean isNameServerEnable() {
            return nameServerEnable;
        }

        public void setNameServerEnable(boolean nameServerEnable) {
            this.nameServerEnable = nameServerEnable;
        }
    }


    public static class NeoTransform {
        private String httpMethod;
        private String serviceGroup;
        private String service;
        private String api;

        public String getHttpMethod() {
            return httpMethod;
        }

        public void setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
        }

        public String getServiceGroup() {
            return serviceGroup;
        }

        public void setServiceGroup(String serviceGroup) {
            this.serviceGroup = serviceGroup;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
        }
    }

    public static class Intercept {
        private boolean using;
        private List<InterceptStrategy> interceptStrategys;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public List<InterceptStrategy> getInterceptStrategys() {
            return interceptStrategys;
        }

        public void setInterceptStrategys(List<InterceptStrategy> interceptStrategys) {
            this.interceptStrategys = interceptStrategys;
        }
    }


    public static class InterceptStrategy {
        private String type;
        private List<Rule> Rules;
        private int interceptTime;
        private int period;
        private int frequency;
        private int interceptRespCode;
        private String interceptRespContent;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Rule> getRules() {
            return Rules;
        }

        public void setRules(List<Rule> rules) {
            Rules = rules;
        }

        public int getInterceptTime() {
            return interceptTime;
        }

        public void setInterceptTime(int interceptTime) {
            this.interceptTime = interceptTime;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public int getInterceptRespCode() {
            return interceptRespCode;
        }

        public void setInterceptRespCode(int interceptRespCode) {
            this.interceptRespCode = interceptRespCode;
        }

        public String getInterceptRespContent() {
            return interceptRespContent;
        }

        public void setInterceptRespContent(String interceptRespContent) {
            this.interceptRespContent = interceptRespContent;
        }
    }




    public static class AntiSpider {
        private boolean using;
        private boolean syncSpider;
        private List<AntiSpiderAction> antiSpiderActions;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public boolean isSyncSpider() {
            return syncSpider;
        }

        public void setSyncSpider(boolean syncSpider) {
            this.syncSpider = syncSpider;
        }

        public List<AntiSpiderAction> getAntiSpiderActions() {
            return antiSpiderActions;
        }

        public void setAntiSpiderActions(List<AntiSpiderAction> antiSpiderActions) {
            this.antiSpiderActions = antiSpiderActions;
        }
    }


    public static class AntiSpiderAction {
        private String validateResult;
        private String proxyUpstreamName;
        private String proxyUpstream;
        private List<Rewrite> rewrites;

        public String getValidateResult() {
            return validateResult;
        }

        public void setValidateResult(String validateResult) {
            this.validateResult = validateResult;
        }

        public String getProxyUpstreamName() {
            return proxyUpstreamName;
        }

        public void setProxyUpstreamName(String proxyUpstreamName) {
            this.proxyUpstreamName = proxyUpstreamName;
        }

        public String getProxyUpstream() {
            return proxyUpstream;
        }

        public void setProxyUpstream(String proxyUpstream) {
            this.proxyUpstream = proxyUpstream;
        }

        public List<Rewrite> getRewrites() {
            return rewrites;
        }

        public void setRewrites(List<Rewrite> rewrites) {
            this.rewrites = rewrites;
        }
    }


    public static class HashKey {
        private String keyName;
        private String keyType;

        public String getKeyName() {
            return keyName;
        }

        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }

        public String getKeyType() {
            return keyType;
        }

        public void setKeyType(String keyType) {
            this.keyType = keyType;
        }
    }

    public static class Config {
        private int quota;
        private List<Rule> rule;
        private String canaryName;
        private String canaryHeader;

        public int getQuota() {
            return quota;
        }

        public void setQuota(int quota) {
            this.quota = quota;
        }

        public List<Rule> getRule() {
            return rule;
        }

        public void setRule(List<Rule> rule) {
            this.rule = rule;
        }

        public String getCanaryName() {
            return canaryName;
        }

        public void setCanaryName(String canaryName) {
            this.canaryName = canaryName;
        }

        public String getCanaryHeader() {
            return canaryHeader;
        }

        public void setCanaryHeader(String canaryHeader) {
            this.canaryHeader = canaryHeader;
        }
    }


    public static class Rule {
        private String type;
        private String keyName;
        private String keyType;
        private String value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getKeyName() {
            return keyName;
        }

        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }

        public String getKeyType() {
            return keyType;
        }

        public void setKeyType(String keyType) {
            this.keyType = keyType;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Canary {
        private boolean using;
        private HashKey hashKey;
        private List<Config> configs;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public HashKey getHashKey() {
            return hashKey;
        }

        public void setHashKey(HashKey hashKey) {
            this.hashKey = hashKey;
        }

        public List<Config> getConfigs() {
            return configs;
        }

        public void setConfigs(List<Config> configs) {
            this.configs = configs;
        }
    }


    public static class CheckList {
        private boolean using;
        private String productLine;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public String getProductLine() {
            return productLine;
        }

        public void setProductLine(String productLine) {
            this.productLine = productLine;
        }
    }

    public static class ExceptionModule {
        private boolean using;
        private String model;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }

    public static class ValidateRequest {
        private Boolean using;
        private String headerValidateType;
        private String SessionValidateType;

        public Boolean getUsing() {
            return using;
        }

        public void setUsing(Boolean using) {
            this.using = using;
        }

        public String getHeaderValidateType() {
            return headerValidateType;
        }

        public void setHeaderValidateType(String headerValidateType) {
            this.headerValidateType = headerValidateType;
        }

        public String getSessionValidateType() {
            return SessionValidateType;
        }

        public void setSessionValidateType(String sessionValidateType) {
            SessionValidateType = sessionValidateType;
        }
    }

    public static class TransProtocol {
        private boolean using;
        private String TargetProtocol;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public String getTargetProtocol() {
            return TargetProtocol;
        }

        public void setTargetProtocol(String targetProtocol) {
            TargetProtocol = targetProtocol;
        }
    }

    public static class Compress {
        private boolean using;
        private String compressType;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public String getCompressType() {
            return compressType;
        }

        public void setCompressType(String compressType) {
            this.compressType = compressType;
        }
    }

    public static class Decompress {
        private boolean using;
        private String decompressType;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public String getDecompressType() {
            return decompressType;
        }

        public void setDecompressType(String decompressType) {
            this.decompressType = decompressType;
        }
    }


    public static class Crypt {
        private boolean using;
        private String CryptType;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public String getCryptType() {
            return CryptType;
        }

        public void setCryptType(String cryptType) {
            CryptType = cryptType;
        }
    }

    public static class Decrypt {
        private boolean using;
        private String decryptType;
        private List<String> decryptWhiteList;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public String getDecryptType() {
            return decryptType;
        }

        public void setDecryptType(String decryptType) {
            this.decryptType = decryptType;
        }

        public List<String> getDecryptWhiteList() {
            return decryptWhiteList;
        }

        public void setDecryptWhiteList(List<String> decryptWhiteList) {
            this.decryptWhiteList = decryptWhiteList;
        }
    }



    public static class ConfigInfo extends JanusConfig {
        String upstreamName;
        List<JanusServer> subServers;

        public String getUpstreamName() {
            return upstreamName;
        }

        public void setUpstreamName(String upstreamName) {
            this.upstreamName = upstreamName;
        }

        public List<JanusServer> getSubServers() {
            return subServers;
        }

        public void setSubServers(List<JanusServer> subServers) {
            this.subServers = subServers;
        }
    }

    public static class JanusServer {
        String ip;
        Integer port;
        Integer weight;
        Boolean enable;
        Integer maxFails;
        Integer failTimeOut;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public Integer getMaxFails() {
            return maxFails;
        }

        public void setMaxFails(Integer maxFails) {
            this.maxFails = maxFails;
        }

        public Integer getFailTimeOut() {
            return failTimeOut;
        }

        public void setFailTimeOut(Integer failTimeOut) {
            this.failTimeOut = failTimeOut;
        }
    }

    public static class ListConfigInfo {
        List<ConfigInfo> configInfos;

        public List<ConfigInfo> getConfigInfos() {
            return configInfos;
        }

        public void setConfigInfos(List<ConfigInfo> configInfos) {
            this.configInfos = configInfos;
        }
    }


    public static class ConfigResponse extends Response {
        int code;
        String msg;
        PageConfigResponse result;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public PageConfigResponse getResult() {
            return result;
        }

        public void setResult(PageConfigResponse result) {
            this.result = result;
        }
    }


    public static class PageConfigResponse extends Response.PageResponse {
        List<JanusConfig> pageData;
        int totalCount;

        public List<JanusConfig> getPageData() {
            return pageData;
        }

        public void setPageData(List<JanusConfig> pageData) {
            this.pageData = pageData;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }

    public static class ConfigDetailResponse extends Response{
        private JanusConfig result;

        public JanusConfig getResult() {
            return result;
        }

        public void setResult(JanusConfig result) {
            this.result = result;
        }
    }

    public static class RequestValidateSandbox {
        boolean using;
        String validateType;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public String getValidateType() {
            return validateType;
        }

        public void setValidateType(String validateType) {
            this.validateType = validateType;
        }
    }


    public static class SessionValidateSandbox {
        boolean using;
        String validateType;

        public boolean isUsing() {
            return using;
        }

        public void setUsing(boolean using) {
            this.using = using;
        }

        public String getValidateType() {
            return validateType;
        }

        public void setValidateType(String validateType) {
            this.validateType = validateType;
        }
    }


}
