package program.tiger.sword.common.respone;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;
import program.tiger.sword.common.utils.AesUtil;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

/**
 * @author junhu.li
 * @ClassName TestRespone
 * @Description TODO
 * @date 2019-02-2214:48
 * @Version 1.0.0
 */
public class TestRespone {

    private static Map<String, Integer> romeNumberMap = new HashMap<>(16);

    static {
        romeNumberMap.put("I", 1);
        romeNumberMap.put("V", 5);
        romeNumberMap.put("X", 10);
        romeNumberMap.put("L", 50);
        romeNumberMap.put("C", 100);
        romeNumberMap.put("D", 500);
        romeNumberMap.put("M", 1000);
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }


    public static void main(String[] args) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        Map<String, String> param = new HashMap<>();
        param.put("cardNo", "190000000050790923");
        String req = AesUtil.encrypt(JsonUtil.toJson(param), "62Gn4BLVaitvlLNR");
        HttpGet get = new HttpGet("http://hcheng.com/user/UserRankInfo?req=" + req);

        try {
            get.addHeader("host", "hcheng.com");
            get.addHeader("outerfrom", "20000");
            get.addHeader("phonebrand", "iPhone");
            get.addHeader("appname", "com.elong.app");
            get.addHeader("deviceid", "7074A7D9-9080-40D9-868C-759168DE6982");
            get.addHeader("cloudtype", "eLong");
            get.addHeader("accept", "*/*");
            get.addHeader("chid", "ewiphone");
            get.addHeader("channelid", "ewiphone");
            get.addHeader("accept-encoding", "gzip, deflate");
            get.addHeader("user-agent", "ElongClient/2807 CFNetwork/902.2 Darwin/17.7.0");
            get.addHeader("localtime", "1560655596174");
            get.addHeader("phonemodel", "iPhone8,1");
            get.addHeader("version", "9.54.0");
            get.addHeader("network", "Wifi");
            get.addHeader("elongdebugnetworkrequestid", "1560655596_user/UserRankInfo");
            get.addHeader("saviortraceid", "1560655595574");
            get.addHeader("channel", "usercenter");
            get.addHeader("positioning", "0");
            get.addHeader("Compress", "gzip");
            get.addHeader("osversion", "iphone_11.4.1");
            get.addHeader("clienttype", "1");
            get.addHeader("longitude", "116.507764");
            get.addHeader("accept-language", "zh-cn");
            get.addHeader("coorsys", "0");
            get.addHeader("innerfrom", "10000");
            get.addHeader("content-type", "application/x-www-form-urlencoded");
            get.addHeader("sessiontoken", "d947b3f8-1545-4236-9dbc-f73a026e7418622");
            get.addHeader("dimension", "750*1334");
            get.addHeader("guid", "2F14967A-1610-40F7-8C94-7C5E6C2C5478");
            get.addHeader("latitude", "39.987045");
            get.addHeader("cache-control", "no-cache");
            get.addHeader("postman-token", "44d2d0da-54ca-8917-4148-fdcc01b7149e");
            HttpResponse httpResponse = httpClient.execute(get);
            System.out.println(decompress(httpResponse.getEntity().getContent(), "utf8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(5000);
            HttpResponse httpResponse = httpClient.execute(get);
            String out = decompress(httpResponse.getEntity().getContent(), "utf-8");
            ByteArrayInputStream inputStream = new ByteArrayInputStream(out.getBytes("utf-8"));
            int temp = 0;

            while ((temp = inputStream.read()) != -1) {
                System.out.print(temp + " ");
            }
            System.out.println();
            System.out.println(out);
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder().url("http://hcheng.com/user/UserRankInfo?req=" + req).
                get();
        builder.addHeader("host", "hcheng.com");
        builder.addHeader("outerfrom", "20000");
        builder.addHeader("phonebrand", "iPhone");
        builder.addHeader("appname", "com.elong.app");
        builder.addHeader("deviceid", "7074A7D9-9080-40D9-868C-759168DE6982");
        builder.addHeader("cloudtype", "eLong");
        builder.addHeader("accept", "*/*");
        builder.addHeader("chid", "ewiphone");
        builder.addHeader("channelid", "ewiphone");
        builder.addHeader("accept-encoding", "gzip, deflate");
        builder.addHeader("user-agent", "ElongClient/2807 CFNetwork/902.2 Darwin/17.7.0");
        builder.addHeader("localtime", "1560655596174");
        builder.addHeader("phonemodel", "iPhone8,1");
        builder.addHeader("version", "9.54.0");
        builder.addHeader("network", "Wifi");
        builder.addHeader("elongdebugnetworkrequestid", "1560655596_user/UserRankInfo");
        builder.addHeader("saviortraceid", "1560655595574");
        builder.addHeader("channel", "usercenter");
        builder.addHeader("positioning", "0");
        builder.addHeader("Compress", "gzip");
        builder.addHeader("osversion", "iphone_11.4.1");
        builder.addHeader("clienttype", "1");
        builder.addHeader("longitude", "116.507764");
        builder.addHeader("accept-language", "zh-cn");
        builder.addHeader("coorsys", "0");
        builder.addHeader("innerfrom", "10000");
        builder.addHeader("content-type", "application/x-www-form-urlencoded");
        builder.addHeader("sessiontoken", "d947b3f8-1545-4236-9dbc-f73a026e7418622");
        builder.addHeader("dimension", "750*1334");
        builder.addHeader("guid", "2F14967A-1610-40F7-8C94-7C5E6C2C5478");
        builder.addHeader("latitude", "39.987045");
        builder.addHeader("cache-control", "no-cache");
        builder.addHeader("postman-token", "44d2d0da-54ca-8917-4148-fdcc01b7149e");
        final Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String out = decompress(response.body().byteStream(), "utf-8");
                ByteArrayInputStream inputStream = new ByteArrayInputStream(out.getBytes("utf-8"));
                int temp = 0;

                while ((temp = inputStream.read()) != -1) {
                    System.out.print(temp + " ");
                }
                System.out.println();
                System.out.println(out);
            }
        });
    }


    public static String decompress(InputStream is, String charset) throws IOException {
        GZIPInputStream gis = null;
        try {
            gis = new GZIPInputStream(is);
            return new BufferedReader(new InputStreamReader(gis))
                    .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        } finally {
            if (gis != null) {
                gis.close();
            } else {
                is.close();
            }
        }
    }

    public static int reverse(int x) {
        StringBuilder builder = new StringBuilder();
        builder.append(Math.abs((long) x)).reverse();
        boolean flag = false;
        if (x < 0) {
            flag = true;
        }

        int result = 0;
        try {
            result = Integer.parseInt(builder.toString());
        } catch (NumberFormatException e) {

        }
        if (flag) {
            return -result;
        }
        return result;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode tail = new ListNode(0);
        head.next = tail;
        while (l1 != null || l2 != null) {
            if (l1 != null) {
                tail.val += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                tail.val += l2.val;
                l2 = l2.next;
            }
            if (tail.val / 10 >= 1) {
                tail.next = new ListNode(tail.val / 10);
                tail.val = tail.val % 10;
            } else {
                tail.next = new ListNode(0);
            }
            if (l1 == null && l2 == null && tail.next.val == 0) {
                tail.next = null;
            } else {
                tail = tail.next;
            }
        }
        return head.next;
    }

    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length - 1; i++) {
            int temp = target - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (temp == nums[j]) {
                    result[0] = i;
                    result[1] = j;
                }
            }

        }
        return result;
    }


    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        while (x / 10 >= 0) {
            int tmp = x % 10;
            if ((!stack.empty()) && stack.peek() == tmp) {
                stack.pop();
            } else {
                stack.push(tmp);
            }
        }
        if (stack.empty()) {
            return true;
        }
        return false;
    }


    public int romanToInt(String s) {
        if (s == null || s.replaceAll(" ", "").length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int num = 0;
        for (int i = 0; i < chars.length - 1; i++) {
            if (romeNumberMap.get(chars[i]) != null && romeNumberMap.get(chars[i + 1]) != null) {
                if (romeNumberMap.get(chars[i]) < romeNumberMap.get(chars[i + 1])) {
                    num -= romeNumberMap.get(chars[i]);
                } else {
                    num += romeNumberMap.get(chars[i + 1]);
                }
            } else {
                return 0;
            }
        }
        num += romeNumberMap.get(chars[chars.length - 1]);
        return num;
    }

    public String longestCommonPrefix(String[] strs) {
        int len = Integer.MAX_VALUE;
        for (int i = 0; i < strs.length; i++) {
            len = strs[i].length() < len ? strs[i].length() : len;
        }
        for (int i = 0; i < len; i++) {

            for (int j = 1; j < strs.length; j++) {
                if (strs[j].charAt(i) != strs[j - 1].charAt(i)) {
                    return i == 0 ? "" : strs[j].substring(0, i);
                }
            }
        }
        return strs[0].substring(0, len);
    }


    @Test
    public void testLombok() {
        Respone<String> respone = Respone.<String>builder().build();
    }
}
