package program.tiger.sword.common.response;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author junhu.li
 * @ClassName TestValidateSessionToken
 * @Description TODO
 * @date 2019-07-1816:09
 * @Version 1.0.0
 */
public class TestValidateSessionToken {

    public static void main(String[] args) {
        String commonUrl = "http://10.160.84.207:8206/husky/http/send?";
        String req = "req=%7B%22mheaders%22%3A%7B%22DeviceId%22%3A%22TestWeb%22%2C%22Version%22%3A%229.36.0%22%2C%22SessionToken%22%3A%22eef03f78-0c24-470c-851f-ed15b055cae801%22%2C%22MvtConfig%22%3A%22%22%2C%22sessionkey%22%3A%22%22%2C%22ChannelId%22%3A%22ew%22%2C%22pversion%22%3A%221.2%22%2C%22CheckCode%22%3A%22%22%2C%22Aeskey%22%3A%22%22%2C%22Browser%22%3A%22husky%22%2C%22ClientType%22%3A%221%22%7D%2C%22sourcePath%22%3A%22http%3A%2F%2F10.39.34.70%3A8080%2Fmtools%2Fraffle%2FgetProduct%22%2C%22httpMethod%22%3A%22Get%22%2C%22req%22%3A%7B%7D%2C%22path%22%3A%22%22%7D";
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url(commonUrl + req).
                get();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(
                () -> {
                    for (int i = 0; i < 2000; i++) {

                        final Request request = builder.build();
                        Call call = client.newCall(request);
                        Stopwatch watch = Stopwatch.createStarted();
                        call.enqueue(new Callback() {

                            @Override
                            public void onFailure(@NotNull Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                                String resp = new String(response.body().bytes());
                                Map<?, ?> map = Maps.newHashMap();
                                try {
                                    map = JsonUtil.toMap(resp);
                                } catch (Exception e) {

                                }
                                long els = watch.elapsed().getSeconds();
                                if (els > 3 || (Boolean) map.get("IsError")) {
                                    System.out.println(els);
                                }
                                watch.reset();
                            }
                        });
                    }
                }
        );


    }
}
