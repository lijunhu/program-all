package program.tiger.sword.http;

import com.google.common.collect.Maps;
import okhttp3.*;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class AppHotelList {


    public static void main(String[] args) {
        String url = "http://mobile-api2011.elong.com/user/userInfo";
        Map<String, String> headers = Maps.newHashMap();
        
        String body = "req=%7b%22sessionToken%22%3a%22cf0b0186-28ad-4861-9f11-8ecd1e4ceb9901%22%7d";

        Request.Builder builder = new Request.Builder().url(url).
                post(RequestBody.create(body.getBytes()));
        builder.header("dimension", "1242*2688");
        builder.header("usertraceid", "0239EE6A-3D14-4BEC-A5CA-05596B58A4BA");
        builder.header("cloudtype", "eLong");
        builder.header("appname", "com.elong.app");
        builder.header("positioning", "0");
        builder.header("longitude", "116.507834");
        builder.header("traceid", "6A613DB1-DB0D-45D5-844C-3E623B649C92");
        builder.header("channel", "h5_home");
        builder.header("version", "1301001");
        builder.header("priority", "0");
        builder.header("accept-language", "zh-cn");
        builder.header("user-agent", "ElongClient/2103 CFNetwork/1197 Darwin/20.0.0");
        builder.header("phonemodel", "iPhone11,6");
        builder.header("clienttype", "1");
        builder.header("hotelgroup", "1");
        builder.header("connection", "close");
        builder.header("localtime", "1604646388799");
        builder.header("leonid-uri", "/hotel/hotelListV4");
        builder.header("innerfrom", "10000");
        builder.header("osversion", "iphone_14.0.1");
        builder.header("appfrom", "1");
        builder.header("chid", "wxqbh5");
        builder.header("channelid", "wxqbh5");
        builder.header("content-type", "application/x-www-form-urlencoded");
        builder.header("clientip", "10.161.112.78");
        builder.header("saviortraceid", "1604646388739");
        builder.header("elongdebugnetworkrequestid", "1604646388802_hotel/hotelListV4");
        builder.header("idfv", "68CA7C00-6175-403F-9EB4-BFF81DD510CF");
        builder.header("phonebrand", "iPhone");
        builder.header("deviceid", "FF975ADB-0956-49CB-8E38-B88B3E8736F9");
        builder.header("outerfrom", "20000");
        builder.header("latitude", "39.986971");
        builder.header("smdeviceid", "201909061057579f8be63e9015f4f9429052a95005d593015b4ff936b09408");
        builder.header("network", "Wifi");
        builder.header("interceptaction", "0");
        builder.header("coorsys", "0");
        OkHttpClient client = new OkHttpClient();

        final Request request = builder.build();

        ;
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                InputStream is = response.body().byteStream();
                String out = new BufferedReader(new InputStreamReader(is))
                        .lines().parallel().collect(Collectors.joining(System.lineSeparator()));

                System.out.println(out);
            }
        });
    }
}
