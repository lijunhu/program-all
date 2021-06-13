package program.tiger.sword.http;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import program.tiger.sword.common.utils.ExcelUtil;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author junhu.li
 * @ClassName ExportLEonidConfig
 * @Description TODO
 * @date 2019/12/1911:06
 * @Version 1.0.0
 */
public class ExportLeonidConfig {
    private static final String leonidToken = "5c32fcbfa753370007ad4065";
    private static final String assetKey = "5b3dd9f5a753370007f387f4";
    private static final List<String> hosts = Lists.newArrayList(
            "tefe.17usoft.com",
            "m.elong.com",
            "www.ly.com",
            "www.travelgo.com",
            "arsenal.ly.com",
            "wx.17u.cn",
            "janus.17usoft.com",
            "rcdata.17usoft.com",
            "neo.17usoft.com",
            "fish.tclygame.com",
            "m.ly.com",
            "tclygame.com",
            "1jingxi.com",
            "www.1jingxi.com",
            "tcflightopenapi.17usoft.com",
            "m.1jingxi.com",
            "leonidtq.ly.com",
            "x.elong.com",
            "ebkapi.17u.cn",
            "www.jingtuhouse.com",
            "www.jingtuchuxing.com",
            "tw.ly.com",
            "zby.ly.com",
            "en.ly.com",
            "ccrm.17u.cn",
            "leonidapi.17usoft.com",
            "admin.water.17usoft.com",
            "member.ly.com",
            "passport.ly.com",
            "www.meichenghotels.cn",
            "www.meichenghotels.com",
            "ghotel.ly.com",
            "account.tcwyun.com",
            "train.17usoft.net",
            "gny.ly.com",
            "m.t.xiangyula.com",
            "www.xiangyula.cn",
            "www.xiangyula.net",
            "www.xiangyula.com",
            "so.ly.com",
            "httpdns.ly.com",
            "httpdns.t.ly.com",
            "app.ly.com",
            "jpebook.ly.com",
            "ebk.17u.cn",
            "tcinnerapi.17usoft.net",
            "api.17u.cn",
            "appnew.ly.com",
            "jipiao.ly.com",
            "crm.17usoft.com",
            "cmsapi.17usoft.com",
            "resource.17usoft.com",
            "tcservice.17usoft.com",
            "shouji.17u.cn",
            "virtual.ly.com",
            "union.ly.com",
            "www.t.ly.com",
            "ly.com",
            "www4.ly.com"
    );

    @Getter
    @Setter
    public static class ExcelBean {
        private String uri;
        private String host;
        private String rewrite;
    }

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        hosts.forEach(host -> {
            try {
                HttpGet leonidConfigGet = new HttpGet("http://leonidapi.17usoft.com/libraapi2/leonid/v2/uriconfig/config/all?configId=-1&nodeGroupId=5722d47cafb33f0e0068934c&pagenum=1&pagesize=800&host=" + host);
                leonidConfigGet.addHeader("user-token", leonidToken);
                leonidConfigGet.addHeader("asset-key", assetKey);
                HttpResponse httpResponse = httpClient.execute(leonidConfigGet);
                String respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                LeonidConfig.LeonidConfigResponse leonidConfigResponse = JsonUtil.toBean(respStr, LeonidConfig.LeonidConfigResponse.class);

                List<ExcelBean> excelBeans = leonidConfigResponse.getResult().getUriConfigInfo().stream().filter(LeonidConfig::isEnabled).map(configInfo -> {
                    ExcelBean excelBean = new ExcelBean();
                    excelBean.setHost(configInfo.getHost());
                    excelBean.setUri(configInfo.getUri());
                    List<LeonidConfig.Rewrite> rewrites = configInfo.getRewrites();
                    List<String> rewriteStr = rewrites.stream().map(rewrite -> {
                                String text = "";
                                if (StringUtils.isNotEmpty(rewrite.getStart()) && StringUtils.isNotEmpty(rewrite.getEnd())) {
                                    text = Joiner.on("->").join(rewrite.getStart(), rewrite.getEnd());
                                }
                                return text;
                            }
                    ).collect(Collectors.toList());

                    excelBean.setRewrite(Joiner.on("\n").join(rewriteStr));
                    return excelBean;
                }).collect(Collectors.toList());


                List<String> titles = Lists.newArrayList("uri", "host", "rewrite");
                List<String> columns = Lists.newArrayList("uri", "host", "rewrite");
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

                ExcelUtil.data2Book(xssfWorkbook, excelBeans, "北京灰度射手座测试", titles, columns);

                File file = new File("/Users/lijh//Work/move_config/leonid/" + host + ".xlsx");
                FileOutputStream fos = new FileOutputStream(file);
                xssfWorkbook.write(fos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}

