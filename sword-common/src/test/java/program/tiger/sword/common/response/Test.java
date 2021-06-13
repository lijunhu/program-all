package program.tiger.sword.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import program.tiger.sword.common.utils.AesUtil;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author junhu.li
 * @ClassName Test
 * @Description TODO
 * @date 2019-06-2620:53
 * @Version 1.0.0
 */
public class Test {

    public static class Bean {
        private String cardNo;
        @JsonProperty(value = "CardNo")
        private String no;

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }
    }

    public static void main(String[] args) throws Exception {
        String commonUrl = "http://mobileapi.t.ly.com/salm/entrance";
        OkHttpClient client = new OkHttpClient();
        Map<String, String> headers = Maps.newHashMap();
        headers.put("cloudtype", "eLong");
        headers.put("usertraceid", "0C4F5B32-17BC-416B-B5BB-6CF8477BF498");
        headers.put("dimension", "1242*2688");
        headers.put("appname", "com.elong.app");
        headers.put("positioning", "0");
        headers.put("longitude", "116.50777");
        headers.put("traceid", " 820309D0-6A25-4425-A0A8-79F08EC85C0D");
        headers.put("channelid", "ewiphone");
        headers.put("channel", "usercenter");
        headers.put("version", "9.71.0");
        headers.put("priority", "0");
        headers.put("user-agent", "ElongClient/1915 CFNetwork/1197 Darwin/20.0.0");
        headers.put("phonemodel", "iPhone11,6");
        headers.put("clienttype", "1");
        headers.put("compress", "gzip");
        headers.put("osversion", "iphone_14.0.1");
        headers.put("content-type", "application/x-www-form-urlencoded");
        headers.put("saviortraceid", "1603205585311");
        headers.put("sessiontoken", "1dd7aca6-9012-46a0-8448-8902e38121cf622");
        headers.put("coorsys", "0");
        headers.put("elongdebugnetworkrequestid", "1603205588563_user/userInfo");
        headers.put("idfv", "2937F678-4869-431E-BEC9-7C6E385B1F54");
        headers.put("phonebrand", "iPhone");
        headers.put("outerfrom", "20000");
        headers.put("network", "Wifi");
        headers.put("deviceid", "FF975ADB-0956-49CB-8E38-B88B3E8736F9");
        headers.put("localtime", "1603205588561");
        headers.put("accept-encoding", "gzip, deflate");
        headers.put("smdeviceid", "201909061057579f8be63e9015f4f9429052a95005d593015b4ff936b09408");
        headers.put("latitude", "39.987001");
        headers.put("chid", "ewiphone");
        headers.put("innerfrom", "10000");

        headers.put("test-token", "5dc3c98a9725eb0007f0f8b5");
        headers.put("test-source", "H5");

        Map<String, Object> body = Maps.newHashMap();
        body.put("cid", "510");
        body.put("userId", "44713163");
        body.put("deviceId","deviceId");
        body.put("cityId","0201");
        body.put("lat","");
        body.put("lon","");

        Map<String, Object> param = new HashMap<>();
        param.put("body", body);
        param.put("head", headers);

        String req = AesUtil.encrypt(JsonUtil.toJson(param), "iEFd7cLcnFeHJTJX");
        String bodyData = "{\"r\":\"" + req + "\",\"ct\":\"1\",\"v\":\"9.71.0\"}";
        System.out.println(bodyData);

        byte[] dataBytes = GzipCompress(bodyData, Charset.forName("utf-8"));
        Request.Builder builder = new Request.Builder().url(commonUrl).
                post(RequestBody.create(dataBytes));
        builder.header("content-type", "application/gzip");
        builder.header("cookie", "SessionToken=fb10e457-a6eb-49e8-a11d-ad7732214fb901");
        //builder.header("host", "tcmapi.elong.com");
        final Request request = builder.build();

        ;
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {

                Headers headers = response.headers();
                System.out.println(headers.toString());
                GZIPInputStream gis = null;
                InputStream is = response.body().byteStream();
                String out = "";
                try {
                    gis = new GZIPInputStream(is);
                    // gis = new GZIPInputStream(gis);
                    out = new BufferedReader(new InputStreamReader(gis))
                            .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                } finally {
                    if (gis != null) {
                        gis.close();
                    } else {
                        is.close();
                    }
                }
                System.out.println(out.length());
                System.out.println(out);
            }
        });
    }

    public static byte[] GzipCompress(String str, Charset charset) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(charset));
            gzip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
}
