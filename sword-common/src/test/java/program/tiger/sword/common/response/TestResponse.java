package program.tiger.sword.common.response;

import com.google.common.base.Stopwatch;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import program.tiger.sword.common.utils.AesUtil;
import program.tiger.sword.common.utils.JsonUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
public class TestResponse {

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

    @Test
    public void testResponse(){

    }

    public static void main(String[] args) {


        String gzipStr= "H4sIAAAAAAAAA6tWci0qyi9yzk9JVbJSUtKBcH1Ti4sT06EinsVgMSWrtMSc4lQdpeT8vJLUvBKfzOISJavoaqXkjMS8vNQcoGKP/BIgDVcBFHnRuevlvpnPpm14um7Ry2XTnvZOfbF/yvO+DYYGqkDus5mz3u/peLK79eXMCc/XrHmyo+Hl9C3v93Q+69j/YsvO93t6ns1peLl6hqnB09bmpxsaXy7c9rihCWh+ZoqSlaGFpY5SVmlugU9mXjbEofkFqUWJJSCXGugoFUDcXwGUTS0qSEktScwEOa0gvzizJDM/zxNohFJVRmliXkVmYl56SWYBSLIoP6U0GeizPJBWj5LcHNPwRJBEWWpRMVATyJpaHfJ8bGSgCvMW1MeGQM8agoOgE+5VY1NsXrU0p65XUzKLk/NL80osiPVzbC0Ak3Lk6igCAAA=";
        byte[] gzipBytes = gzipStr.getBytes();
        ByteArrayInputStream gzipInputStream = new ByteArrayInputStream(gzipBytes);
        try {
            String outStr = decompress(gzipInputStream,"utf8");
            System.out.println(outStr);
        }catch (IOException e){
            e.printStackTrace();
        }




        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        Map<String, String> param = new HashMap<>();
        param.put("adid", "4d1c11be28c06b7d");
        param.put("cityId","");
        param.put("mac","02:00:00:00:00:00");
        param.put("positionId","6w51222g2119b");
        String req = AesUtil.encrypt(JsonUtil.toJson(param), "4yOdMX6MwAohM15n");
        String commonUrl = "http://10.160.87.123/ad-middle-layer/newad/app?req=";

        try {
            URL url = new URL(commonUrl + URLEncoder.encode(req,"utf8"));
            Stopwatch stopwatch = Stopwatch.createStarted();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(Boolean.TRUE);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("host", "10.160.87.123");
            connection.setRequestProperty("channelid","");
            connection.setRequestProperty("version","9.60.0");
            connection.setRequestProperty("ClientType","1");
            connection.setRequestProperty("DeviceId","5335d6d76708ef2d||5335E6d16708e92d");
//            connection.setRequestProperty("outerfrom", "20000");
//            connection.setRequestProperty("phonebrand", "iPhone");
//            connection.setRequestProperty("appname", "com.elong.app");
//            connection.setRequestProperty("deviceid", "7074A7D9-9080-40D9-868C-759168DE6982");
//            connection.setRequestProperty("cloudtype", "eLong");
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("chid", "ewiphone");
//            connection.setRequestProperty("channelid", "ewiphone");
//            connection.setRequestProperty("accept-encoding", "gzip, deflate");
//            connection.setRequestProperty("user-agent", "ElongClient/2807 CFNetwork/902.2 Darwin/17.7.0");
//            connection.setRequestProperty("localtime", "1560655596174");
//            connection.setRequestProperty("phonemodel", "iPhone8,1");
//            connection.setRequestProperty("version", "9.54.0");
//            connection.setRequestProperty("network", "Wifi");
//            connection.setRequestProperty("elongdebugnetworkrequestid", "1560655596_user/UserRankInfo");
//            connection.setRequestProperty("saviortraceid", "1560655595574");
//            connection.setRequestProperty("channel", "usercenter");
//            connection.setRequestProperty("positioning", "0");
//            connection.setRequestProperty("Compress", "gzip");
//            connection.setRequestProperty("osversion", "iphone_11.4.1");
//            connection.setRequestProperty("clienttype", "1");
//            connection.setRequestProperty("longitude", "116.507764");
//            connection.setRequestProperty("accept-language", "zh-cn");
//            connection.setRequestProperty("coorsys", "0");
//            connection.setRequestProperty("innerfrom", "10000");
//            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("sessiontoken", "d947b3f8-1545-4236-9dbc-f73a026e7418622");
//            connection.setRequestProperty("dimension", "750*1334");
//            connection.setRequestProperty("guid", "2F14967A-1610-40F7-8C94-7C5E6C2C5478");
//            connection.setRequestProperty("latitude", "39.987045");
//            connection.setRequestProperty("cache-control", "no-cache");
//            connection.setRequestProperty("postman-token", "44d2d0da-54ca-8917-4148-fdcc01b7149e");
            System.out.println(decompress(connection.getInputStream(), "utf-8"));
            System.out.println(stopwatch.elapsed().getSeconds());
            connection.disconnect();
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        HttpGet get = new HttpGet(commonUrl + req);
//        try {
//
//            get.addHeader("host", "10.172.20.17");
//            get.addHeader("outerfrom", "20000");
//            get.addHeader("phonebrand", "iPhone");
//            get.addHeader("appname", "com.elong.app");
//            get.addHeader("deviceid", "7074A7D9-9080-40D9-868C-759168DE6982");
//            get.addHeader("cloudtype", "eLong");
//            get.addHeader("accept", "*/*");
//            get.addHeader("chid", "ewiphone");
//            get.addHeader("channelid", "ewiphone");
//            get.addHeader("accept-encoding", "gzip, deflate");
//            get.addHeader("user-agent", "ElongClient/2807 CFNetwork/902.2 Darwin/17.7.0");
//            get.addHeader("localtime", "1560655596174");
//            get.addHeader("phonemodel", "iPhone8,1");
//            get.addHeader("version", "9.54.0");
//            get.addHeader("network", "Wifi");
//            get.addHeader("elongdebugnetworkrequestid", "1560655596_user/UserRankInfo");
//            get.addHeader("saviortraceid", "1560655595574");
//            get.addHeader("channel", "usercenter");
//            get.addHeader("positioning", "0");
//            get.addHeader("Compress", "gzip");
//            get.addHeader("osversion", "iphone_11.4.1");
//            get.addHeader("clienttype", "1");
//            get.addHeader("longitude", "116.507764");
//            get.addHeader("accept-language", "zh-cn");
//            get.addHeader("coorsys", "0");
//            get.addHeader("innerfrom", "10000");
//            get.addHeader("content-type", "application/x-www-form-urlencoded");
//            get.addHeader("sessiontoken", "d947b3f8-1545-4236-9dbc-f73a026e7418622");
//            get.addHeader("dimension", "750*1334");
//            get.addHeader("guid", "2F14967A-1610-40F7-8C94-7C5E6C2C5478");
//            get.addHeader("latitude", "39.987045");
//            get.addHeader("cache-control", "no-cache");
//            get.addHeader("postman-token", "44d2d0da-54ca-8917-4148-fdcc01b7149e");
//            HttpResponse httpResponse = httpClient.execute(get);
//            Header[] headers = httpResponse.getAllHeaders();
//            for (Header header : headers) {
//                System.out.println(header.getName() + "\t\t\t\t" + header.getValue());
//            }
//
//            System.out.println(decompress(httpResponse.getEntity().getContent(), "utf8"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Thread.sleep(5000);
//            HttpResponse httpResponse = httpClient.execute(get);
//            String out = decompress(httpResponse.getEntity().getContent(), "utf-8");
//            System.out.println();
//            System.out.println(out);
//            httpClient.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        OkHttpClient client = new OkHttpClient();
//        Request.Builder builder = new Request.Builder().url(commonUrl + req).
//                get();
//        builder.addHeader("host", "10.172.20.17");
//        builder.addHeader("outerfrom", "20000");
//        builder.addHeader("phonebrand", "iPhone");
//        builder.addHeader("appname", "com.elong.app");
//        builder.addHeader("deviceid", "7074A7D9-9080-40D9-868C-759168DE6982");
//        builder.addHeader("cloudtype", "eLong");
//        builder.addHeader("accept", "*/*");
//        builder.addHeader("chid", "ewiphone");
//        builder.addHeader("channelid", "ewiphone");
//        builder.addHeader("accept-encoding", "gzip, deflate");
//        builder.addHeader("user-agent", "ElongClient/2807 CFNetwork/902.2 Darwin/17.7.0");
//        builder.addHeader("localtime", "1560655596174");
//        builder.addHeader("phonemodel", "iPhone8,1");
//        builder.addHeader("version", "9.54.0");
//        builder.addHeader("network", "Wifi");
//        builder.addHeader("elongdebugnetworkrequestid", "1560655596_user/UserRankInfo");
//        builder.addHeader("saviortraceid", "1560655595574");
//        builder.addHeader("channel", "usercenter");
//        builder.addHeader("positioning", "0");
//        builder.addHeader("Compress", "gzip");
//        builder.addHeader("osversion", "iphone_11.4.1");
//        builder.addHeader("clienttype", "1");
//        builder.addHeader("longitude", "116.507764");
//        builder.addHeader("accept-language", "zh-cn");
//        builder.addHeader("coorsys", "0");
//        builder.addHeader("innerfrom", "10000");
//        builder.addHeader("content-type", "application/x-www-form-urlencoded");
//        builder.addHeader("sessiontoken", "d947b3f8-1545-4236-9dbc-f73a026e7418622");
//        builder.addHeader("dimension", "750*1334");
//        builder.addHeader("guid", "2F14967A-1610-40F7-8C94-7C5E6C2C5478");
//        builder.addHeader("latitude", "39.987045");
//        builder.addHeader("cache-control", "no-cache");
//        builder.addHeader("postman-token", "44d2d0da-54ca-8917-4148-fdcc01b7149e");
//        final Request request = builder.build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                Headers headers = response.headers();
//                System.out.println(headers.toString());
//                GZIPInputStream gis = null;
//                InputStream inputStream = response.body().byteStream();
//                String out = "";
//                try {
//                    gis = new GZIPInputStream(inputStream);
//                    //gis = new GZIPInputStream(gis);
//                    gis = new GZIPInputStream(gis);
//                    out = new BufferedReader(new InputStreamReader(gis))
//                            .lines().parallel().collect(Collectors.joining(System.lineSeparator()));
//                } finally {
//                    if (gis != null) {
//                        gis.close();
//                    } else {
//                        inputStream.close();
//                    }
//                }
//
//                System.out.println(out);
//            }
//        });
    }






    public static String decompress(InputStream is, String charset) throws IOException {
        GZIPInputStream gis = null;
        try {
            //gis = new GZIPInputStream(is);
            //gis = new GZIPInputStream(gis);
            return new BufferedReader(new InputStreamReader(is))
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

}
