package program.tiger.sword.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import program.tiger.sword.common.utils.ExcelUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

public class TestExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(TestExcelUtil.class);


    public static class EAppOrder {
        private String version;
        private String clientType;
        private String count;

        public EAppOrder() {
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getClientType() {
            return clientType;
        }

        public void setClientType(String clientType) {
            this.clientType = clientType;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "EAppOrder{" +
                    "version='" + version + '\'' +
                    ", clientType='" + clientType + '\'' +
                    ", count='" + count + '\'' +
                    '}';
        }
    }

    public static class EAppRequest {
        private String version;
        private String clientType;
        private String count;

        public EAppRequest() {
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getClientType() {
            return clientType;
        }

        public void setClientType(String clientType) {
            this.clientType = clientType;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "EAppRequest{" +
                    "version='" + version + '\'' +
                    ", clientType='" + clientType + '\'' +
                    ", count='" + count + '\'' +
                    '}';
        }
    }

    @Test
    public void compareEAppOrderAndRequests() {
        File file = new File("/Users/lijh/Downloads/E侧app订单_20201107-20201208.xlsx");
        List<EAppOrder> orders = Lists.newArrayList();
        List<EAppRequest> requests = Lists.newArrayList();
        try {
            ExcelUtil.setSheetNum(0);
            orders = ExcelUtil.readExcel(file, EAppOrder.class);
            ExcelUtil.setSheetNum(1);
            requests = ExcelUtil.readExcel(file, EAppRequest.class);
        } catch (Exception e) {
            logger.error("读取Excle异常", e);
        }

        Map<String, Double> orderMap = Maps.newHashMap();
        Map<String, Double> requestMap = Maps.newHashMap();
        String key;
        for (EAppOrder order : orders) {
            key = order.getVersion() + "-" + StringUtils.substringBefore(order.getClientType(), ".");
            Double count = orderMap.get(key);
            if (count != null) {
                count = Double.parseDouble(order.getCount()) + count;
                orderMap.put(key, count);
            } else {
                orderMap.put(key, Double.parseDouble(order.getCount()));
            }
        }

        for (EAppRequest request : requests) {
            key = request.getVersion() + "-" + StringUtils.substringBefore(request.getClientType(), ".");
            Double count = requestMap.get(key);
            if (count != null) {
                count = Double.parseDouble(request.getCount()) + count;
                requestMap.put(key, count);
            } else {
                requestMap.put(key, Double.parseDouble(request.getCount()));
            }
        }

        List<Pair<String, String>> missOrderVersion = Lists.newArrayList();
        for (EAppRequest request : requests) {
            key = request.getVersion() + "-" + StringUtils.substringBefore(request.getClientType(), ".");
            if (orderMap.get(key) == null) {
                Pair<String, String> pair = new ImmutablePair<>(StringUtils.substringBefore(key, "-"),
                        StringUtils.substringAfter(key, "-"));
                missOrderVersion.add(pair);
            }
        }

        missOrderVersion.sort((p1, p2) -> StringUtils.compare(p1.getLeft(), p2.getLeft()));

        for (Pair<String, String> pair : missOrderVersion) {
            System.out.println(pair.getLeft() + "\t" + pair.getRight());
        }

        System.out.println("++++++++++++++++++++++++++++");
        for (EAppOrder order : orders) {
            key = order.getVersion() + "-" + StringUtils.substringBefore(order.getClientType(), ".");
            if (requestMap.get(key) == null) {
                System.out.println(order.getVersion() + "\t" + StringUtils.substringBefore(order.getClientType(), "."));
            }
        }
    }

}
