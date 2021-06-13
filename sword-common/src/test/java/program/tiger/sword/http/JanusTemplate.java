package program.tiger.sword.http;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author junhu.li
 * @ClassName JanusTemplate
 * @Description TODO
 * @date 2019-07-1911:21
 * @Version 1.0.0
 */
@Getter
@Setter
public class JanusTemplate {
    String id;
    String templateName;
    private JanusConfig.BaseConfig baseConfig;
    private JanusConfig.IpLocation ipLocation;
    private JanusConfig.PageCache pageCache;
    private JanusConfig.Canary canary;
    private JanusConfig.ValidateRequest validateRequest;
    private JanusConfig.TransProtocol transProtocol;
    private JanusConfig.Compress compress;
    private JanusConfig.Decompress decompress;
    private JanusConfig.Crypt crypt;
    private JanusConfig.Decrypt decrypt;
    private JanusConfig.AntiSpider antiSpider;
    private JanusConfig.Intercept intercept;
    private String createUserId;
    private String createUserName;
    private Date createTime;
    private Date updateTime;
    private String updateUserId;
    private String updateUserName;

    @Setter
    @Getter
    public static class JanusTemplates{
       private List<JanusTemplate> templateInfos;
    }

    @Setter
    @Getter
    public static  class TemplateResponse extends Response{
        private int code;
        private String msg;
        private JanusTemplate.PageTemplateResponse result;
    }

    @Getter
    @Setter
    public  static class PageTemplateResponse extends Response.PageResponse{
        private JanusTemplates pageData;
        private int totalCount;
    }
}
