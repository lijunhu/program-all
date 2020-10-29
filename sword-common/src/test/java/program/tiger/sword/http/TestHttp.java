package program.tiger.sword.http;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author junhu.li
 * @ClassName TestHttp
 * @Description TODO
 * @date 2019-05-0811:33
 * @Version 1.0.0
 */
public class TestHttp {


    public static void main(String[] args) throws InterruptedException {


        JanusConfig janusConfig = new JanusConfig();

        //解密
        JanusConfig.Decrypt decrypt = new JanusConfig.Decrypt();
        decrypt.setUsing(true);
        decrypt.setDecryptType("AES");

        //响应压缩
        JanusConfig.Compress compress = new JanusConfig.Compress();
        compress.setUsing(true);
        compress.setCompressType("gzip");
        //请求解压
        JanusConfig.Decompress decompress = new JanusConfig.Decompress();
        decompress.setUsing(true);
        decompress.setDecompressType("gzip");
        //异常处理模块
        JanusConfig.ExceptionModule exceptionModule = new JanusConfig.ExceptionModule();
        exceptionModule.setUsing(true);
        exceptionModule.setModel("elong");
        //协议转化
        JanusConfig.TransProtocol transProtocol = new JanusConfig.TransProtocol();
        transProtocol.setUsing(true);
        transProtocol.setTargetProtocol("mapi");


        janusConfig.setDecrypt(decrypt);
        janusConfig.setCompress(compress);
        janusConfig.setDecompress(decompress);
        janusConfig.setException(exceptionModule);
        janusConfig.setTransProtocol(transProtocol);

       /* String mtoolsPath = "/Users/lijh/test/mtools.xml";
        JanusConfig.BaseConfig mtoolsBaseConfig = new JanusConfig.BaseConfig();
        mtoolsBaseConfig.setProxyPassType("server");
        mtoolsBaseConfig.setTemplateName("mobile_mtools模版");
        mtoolsBaseConfig.setServerId("5d006f003a1c340b51523001");
        mtoolsBaseConfig.setHosts(Lists.newArrayList("10.160.84.108"));
        janusConfig.setBaseConfig(mtoolsBaseConfig);

        //checklist
        JanusConfig.CheckList checkList = new JanusConfig.CheckList();
        checkList.setUsing(true);
        checkList.setProductLine("mtools");
        janusConfig.setCheckList(checkList);
        addJanusConfig(mtoolsPath, "mtools", janusConfig);*/


/*        String ucenterPAth = "/Users/lijh/test/ucenter.xml";
        JanusConfig.BaseConfig ucenterBaseConfig = new JanusConfig.BaseConfig();

        ucenterBaseConfig.setServerId("5d006f003a1c340b51523001");
        ucenterBaseConfig.setHosts(Lists.newArrayList("10.160.84.108"));
        ucenterBaseConfig.setProxyPassType("server");
        janusConfig.setBaseConfig(ucenterBaseConfig);
        JanusConfig.CheckList checkList = new JanusConfig.CheckList();
        checkList.setUsing(true);
        checkList.setProductLine("ucenter");
        janusConfig.setCheckList(checkList);
        addJanusConfig(ucenterPAth, "ucenter", janusConfig);*/


        String myelongPath = "/Users/lijh/test/myelong.xml";
        JanusConfig.BaseConfig myelongBaseConfig = new JanusConfig.BaseConfig();
        JanusConfig.DefaultUpstream defaultUpstream = new JanusConfig.DefaultUpstream();

        defaultUpstream.setServerId("5d006f003a1c340b51523001");
        myelongBaseConfig.setHosts(Lists.newArrayList("10.160.84.108"));
        defaultUpstream.setProxyPassType("server");
        JanusConfig.CheckList checkList = new JanusConfig.CheckList();
        checkList.setUsing(true);
        checkList.setProductLine("myelong");
        janusConfig.setCheckList(checkList);
        janusConfig.setBaseConfig(myelongBaseConfig);
        addJanusConfig(myelongPath, "myelong", janusConfig);








        /*final TestHttp distrubuteLimit = new TestHttp();
        final CountDownLatch latch = new CountDownLatch(1);//两个工人的协作
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        latch.await();
                        String rev = distrubuteLimit.sendGet("http://10.100.202.125:8888/tigerlee/janus/test/", null);
                        System.out.println(rev);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
            Thread.sleep(1000);
        }
        String str = "{\"body\":{\"validateResult\":-2,\"interceptAction\":2},\"errTrace\":null,\"retcode\":\"0\",\"retdesc\":\"\",\"serverIp\":\"10.70.6.119\",\"timeStr\":null}";
        latch.countDown();
        System.in.read();*/
    }


    public static void addJanusConfig(String xmlFilePath, String title, JanusConfig janusConfig) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(xmlFilePath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        Element ServiceDefineImpl = root.element("ServiceDefineImpl");
        Element upChainList = ServiceDefineImpl.element("upChainList");
        Element ChainHandlerDefineImpl = upChainList.element("com.elong.mobile.servitization.metadata.define.embedder.impl.ChainHandlerDefineImpl");
        Element EncryptDefineImpl = upChainList.element("com.elong.mobile.servitization.metadata.define.encrypt.impl.EncryptDefineImpl");

        Element RequestCheckDefineImpl = upChainList.element("com.elong.mobile.servitization.metadata.define.request.impl.RequestCheckDefineImpl");
        Element ignoreCheckUrllist = RequestCheckDefineImpl.element("ignoreCheckUrllist");
        List<Element> ignoreCheckUrlLits = ignoreCheckUrllist.elements();


        Element DefenceDefineImpl = upChainList.element("com.elong.mobile.servitization.metadata.define.defence.impl.DefenceDefineImpl");
        Element SessionDefineImpl = upChainList.element("com.elong.mobile.servitization.metadata.define.session.impl.SessionDefineImpl");
        Element strategyMap = SessionDefineImpl.element("strategyMap");
        List<Element> entrys = strategyMap.elements();

        Element ProxyDefineImpl = upChainList.element("com.elong.mobile.servitization.metadata.define.proxy.impl.ProxyDefineImpl");
        Element serviceModuleList = ProxyDefineImpl.element("serviceModuleList");
        List<Element> serviceModules = serviceModuleList.elements();

        //生成配置项
        Map<String, JanusConfig> janusConfigMap = Maps.newHashMap();
        System.out.println("-----------------" + "rewrite策略" + "---------------------------");
        for (Element element : serviceModules) {
            Element SourceService = element.element("SourceService");
            String sourceUri = SourceService.attribute("path").getValue();
            Element TargetService = element.element("TargetService");
            String targetUri = TargetService.attribute("serviceName").getValue();
            JanusConfig config = new JanusConfig();
            JanusConfig.BaseConfig baseConfig = null;
            try {
                baseConfig = janusConfig.getBaseConfig().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            baseConfig.setUri(sourceUri);
            baseConfig.setUsing(true);
            baseConfig.setGroupId("5cfb7443b1e3c1519246ce42");
            JanusConfig.DefaultUpstream defaultUpstream = new JanusConfig.DefaultUpstream();
            //baseConfig.setGroupId("5cfdc23bb1e3c14401608cfb");
            if (!StringUtils.equals(sourceUri, targetUri)) {
                JanusConfig.Rewrite rewrite = new JanusConfig.Rewrite();
                rewrite.setType("break");
                rewrite.setCond("");
                rewrite.setStart(sourceUri);
                rewrite.setEnd(targetUri);
                defaultUpstream.setRewrites(Lists.newArrayList(rewrite));
            }
            baseConfig.setDefaultUpstream(defaultUpstream);
            baseConfig.setNextUpstreamRules(Lists.newArrayList("error", "timeout"));
            config.setBaseConfig(baseConfig);
            config.setDecompress(janusConfig.getDecompress());
            config.setCompress(janusConfig.getCompress());
            config.setDecrypt(janusConfig.getDecrypt());
            config.setCrypt(janusConfig.getCrypt());
            config.setCheckList(janusConfig.getCheckList());
            config.setException(janusConfig.getException());
            config.setTransProtocol(janusConfig.getTransProtocol());
            janusConfigMap.put(sourceUri, config);

        }
        List<Element> downChainList = ServiceDefineImpl.elements();

        System.out.println("-----------------" + "session校验策略" + "---------------------------");
        for (Element element : entrys) {
            if (element != null) {
                String key = element.attribute("key").getValue();
                String value = element.attribute("value").getValue();
                List<String> valueList = Splitter.on(".").splitToList(value);
                JanusConfig config = janusConfigMap.get(key);
                if (config != null) {
                    JanusConfig.ValidateRequest validateRequest = new JanusConfig.ValidateRequest();
                    validateRequest.setUsing(Boolean.TRUE);
                    validateRequest.setHeaderValidateType("elong");
                    validateRequest.setSessionValidateType(valueList.get(valueList.size() - 1));
                    config.setValidateRequest(validateRequest);
                }
            }
        }

        System.out.println("-----------------" + "设置不需要请求校验的数据" + "---------------------------");

        for (Element element : ignoreCheckUrlLits) {
            String value = element.attribute("value").getValue();
            JanusConfig config = janusConfigMap.get(value);
            if (config != null) {
                JanusConfig.ValidateRequest validateRequest = config.getValidateRequest() == null ? new JanusConfig.ValidateRequest() : config.getValidateRequest();
                validateRequest.setUsing(Boolean.FALSE);
                validateRequest.setSessionValidateType("");
                config.setValidateRequest(validateRequest);
            }
        }


        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //HttpPost post = new HttpPost("http://janus.t.17usoft.com/janus-api/api/uriconfig/5d05ebb83457240007d1fa2e/config");
        HttpPost post = new HttpPost("http://10.100.202.119:6010/janus-api/api/uriconfig/5cfb7443b1e3c1519246ce42/config");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("user-token", "5cfb2d9bb1e3c1519246ce24");
        //post.addHeader("user-token", "5cfb2d9bb1e3c1519246ce24");
        List<JanusConfig> failedConfigs = Lists.newArrayList();
        for (Map.Entry<String, JanusConfig> entry : janusConfigMap.entrySet()) {
            try {
                String configJson = JsonUtil.toJson(entry.getValue());
                StringEntity entity = new StringEntity(configJson, "utf-8");
                post.setEntity(entity);
                HttpResponse httpResponse = httpClient.execute(post);
                String respStr = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                Response resp = JsonUtil.toBean(respStr, Response.class);
                if (resp.getCode() != 0) {
                    failedConfigs.add(entry.getValue());
                }
                httpResponse.getEntity().getContent().close();
            } catch (Exception e) {
                failedConfigs.add(entry.getValue());
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(title + "失败" + failedConfigs.size() + "--------------------");
        for (JanusConfig config : failedConfigs) {
            System.out.println(config.getBaseConfig().getUri());
        }
    }

    public String sendGet(String httpurl, Map<String, Object> params) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            assert connection != null : "连接为空";
            connection.disconnect();// 关闭远程连接
        }

        return result;
    }
}
