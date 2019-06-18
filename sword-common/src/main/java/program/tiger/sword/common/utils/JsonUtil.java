package program.tiger.sword.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;
import java.util.Map;

/**
 * @author junhu.li
 * @ClassName JsonUtil
 * @Description Json工具类
 * @date 2019-02-2219:00
 * @Version 1.0.0
 */
@Slf4j
public class JsonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public void JsonUtil() {

    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    //objectMapper设置属性
    static{

    }

    /**
     * object 转 json
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("write to json string error:" + obj, e);
            return null;
        }
    }

    /**
     * json 转 bean
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T toBean(String jsonStr, Class<T> clazz) throws Exception {
        return objectMapper.readValue(jsonStr, clazz);
    }

    /**
     * json 转map
     * @param jsonStr
     * @return
     * @throws Exception
     */
    public static Map<String, Object> toMap(String jsonStr) throws Exception {
        if (jsonStr != null && !"".equals(jsonStr)) {
            return objectMapper.readValue(jsonStr, Map.class);
        } else {
            return null;
        }
    }

    /**
     * json string 转 map with javaBean
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> Map<String, T> toMapBean(String jsonStr, Class<T> clazz) throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr, new TypeReference() {
        });
        Map<String, T> result = Maps.newHashMap();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), mapToBean(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json 转数组
     * @param jsonArrayStr
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> toList(String jsonArrayStr, Class<T> clazz) throws Exception {
        List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr, new TypeReference<List<T>>() {
        });
        List<T> result = Lists.newArrayList();
        for (Map<String, Object> map : list) {
            result.add(mapToBean(map, clazz));
        }
        return result;
    }

    /**
     * map 转 bean
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     *
     * @param value
     * @param callback
     * @return
     */
    public static MappingJacksonValue jsonp(Object value, Class callback) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(value);
        mappingJacksonValue.setSerializationView(callback);
        return mappingJacksonValue;
    }

}
