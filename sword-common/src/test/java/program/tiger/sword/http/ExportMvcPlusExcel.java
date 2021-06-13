package program.tiger.sword.http;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
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
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author junhu.li
 * @ClassName ExportMvcPlusExcel
 * @Description TODO
 * @date 2019-08-2211:02
 * @Version 1.0.0
 */
public class ExportMvcPlusExcel {


    @Getter
    @Setter
    public static class ExcleBean {
        @Excel(name = "uri")
        private String uri;
        private String host;
        private String leonidCurl;
        private String tefeCurl;
    }


    private static final String groupId = "5d4ac8d39725eb0007682790";
    //private static final String groupId = "5d512ac9b1e3c108063e7118";    //测试环境


    private static final String janusToken = "5d07036b34572400072b4a70";
    //private static final String janusToken = "5cfb2d9bb1e3c1519246ce24"; //测试环境


    private static final String janusHost = "http://janus.17usoft.com";
    //private static final String janusHost = "http://10.100.202.119:6010";//测试环境


    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet janusConfigGet = new HttpGet(janusHost + "/janus-api/api/uriconfig/" + groupId + "/config/list?pageSize=500&host=leonidapi.17usoft.com&using=1");
        janusConfigGet.addHeader("user-token", janusToken);
        HttpResponse httpResponse = httpClient.execute(janusConfigGet);
        String respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        JanusConfig.ConfigResponse configResponse = JsonUtil.toBean(respStr, JanusConfig.ConfigResponse.class);
        List<JanusConfig> janusConfigInfos = configResponse.result.pageData;


        List<ExcleBean> excleData = Lists.newArrayList();
        janusConfigInfos.forEach(janusConfigInfo -> {
            ExcleBean excleBean = new ExcleBean();
            //excleBean.setUri(janusConfigInfo.getBaseConfig().getUri());
            //excleBean.setHost(janusConfigInfo.getBaseConfig().getHosts().get(0));
            String leonidCurl = "curl http://" + "172.18.174.227" +
                    janusConfigInfo.getBaseConfig().getUri() + " -H 'Host:" + janusConfigInfo.getBaseConfig().getHosts().get(0) + "'";
            //excleBean.setLeonidCurl(leonidCurl);
            String tefeCurl = "curl http://" + "172.18.168.2" + janusConfigInfo.getBaseConfig().getUri() + " -H 'Host:" + janusConfigInfo.getBaseConfig().getHosts().get(0) + "'";
            //excleBean.setTefeCurl(tefeCurl);
            excleData.add(excleBean);
        });

        List<String> titles = Lists.newArrayList("uri", "host", "狮子座curl", "新网关Curl");
        List<String> columns = Lists.newArrayList("uri", "host", "leonidCurl", "tefeCurl");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        ExcelUtil.data2Book(xssfWorkbook, excleData, "北京灰度射手座测试", titles, columns);
        File file = new File("/Users/lijh/test/111/bj_prod_leonidapi.17usoft.com.xlsx");
        FileOutputStream fos = new FileOutputStream(file);
        xssfWorkbook.write(fos);
    }
}
