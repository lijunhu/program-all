package program.tiger.sword.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author junhu.li
 * @ClassName TestHttp
 * @Description TODO
 * @date 2019-05-0811:33
 * @Version 1.0.0
 */
public class TestHttp {


    public static void main(String[] args) throws IOException, InterruptedException {
        final TestHttp distrubuteLimit = new TestHttp();
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
        System.in.read();
    }


    public String sendGet(String httpurl, Map<String,Object> params){
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
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

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }
}
