package program.tiger.sword.http.reverse.address;

import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import program.tiger.sword.common.utils.JsonUtil;
import program.tiger.sword.http.reverse.address.entity.CityInfo;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CompareCityInfo {

    private static final Logger logger = LoggerFactory.getLogger(CompareCityInfo.class);

    private static final String cityInfoUrl = "http://imis.corp.elong.com:8080/imis/?r=api/GetJsonData&Type=list";

    private static final String newCityInfoUrl = "http://imis.corp.elong.com:8080/imis/?r=api/GetJsonData&Type=mixList&isGAT=1";


    private <T> List<T> getCityInfo(String url, Class<T> clazz) {
        List<T> ret = Lists.newArrayList();

        ConnectionProvider provider = ConnectionProvider.create("reverse.address");

        HttpClient client = HttpClient.create(provider);

        String response = client.get().uri(url).responseContent().aggregate().asString(StandardCharsets.UTF_8).block();
        try {
            ret = JsonUtil.toList(response, clazz);
        } catch (Exception e) {
            logger.error("解析接口数据失败，错误信息：", e);
        }
        return ret;
    }


    @Test
    public void TestGetCityInfo() {
        CompareCityInfo c = new CompareCityInfo();

        List<CityInfo> olCities = c.getCityInfo(cityInfoUrl, CityInfo.class);
        Map<String, CityInfo> oldCityMap = olCities.parallelStream().collect(Collectors.toMap(
                new Function<CityInfo, String>() {
                    @Override
                    public String apply(CityInfo cityInfo) {
                        return cityInfo.getProvinceId() + "_" + cityInfo.getCityId();
                    }
                }, new Function<CityInfo, CityInfo>() {
                    @Override
                    public CityInfo apply(CityInfo cityInfo) {
                        return cityInfo;
                    }
                }, new BinaryOperator<CityInfo>() {
                    @Override
                    public CityInfo apply(CityInfo c1, CityInfo c2) {
                        return c1;
                    }
                }, new Supplier<Map<String, CityInfo>>() {
                    @Override
                    public Map<String, CityInfo> get() {
                        return Maps.newHashMap();
                    }
                }));

        List<CityInfo> newCities = c.getCityInfo(newCityInfoUrl, CityInfo.class);
        Map<String, CityInfo> newCityMap = newCities.parallelStream().collect(Collectors.toMap(
                new Function<CityInfo, String>() {
                    @Override
                    public String apply(CityInfo cityInfo) {
                        return cityInfo.getProvinceId() + "_" + cityInfo.getCityId();
                    }
                }, new Function<CityInfo, CityInfo>() {
                    @Override
                    public CityInfo apply(CityInfo cityInfo) {
                        return cityInfo;
                    }
                }, new BinaryOperator<CityInfo>() {
                    @Override
                    public CityInfo apply(CityInfo c1, CityInfo c2) {
                        return c1;
                    }
                }, new Supplier<Map<String, CityInfo>>() {
                    @Override
                    public Map<String, CityInfo> get() {
                        return Maps.newHashMap();
                    }
                }
        ));

        for (CityInfo cityInfo : newCities) {
            if (null == oldCityMap.get(cityInfo.getProvinceId() + "_" + cityInfo.getCityId())) {
                logger.info("老数据不存在:" + JsonUtil.toJson(cityInfo));
            }
        }

        for (CityInfo cityInfo : olCities) {
            if (null == newCityMap.get(cityInfo.getProvinceId() + "_" + cityInfo.getCityId())) {
                logger.info("新数据不存在:" + JsonUtil.toJson(cityInfo));
            }
        }
    }

}
