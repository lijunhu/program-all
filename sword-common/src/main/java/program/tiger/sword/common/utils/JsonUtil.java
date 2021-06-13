package program.tiger.sword.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
public class JsonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public JsonUtil() {

    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    //objectMapper设置属性
    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * object 转 json
     *
     * @param obj obj
     * @return String
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Object to Json failed");
        }
    }

    /**
     * json 转 bean
     *
     * @param jsonStr jsonStr
     * @param clazz   class
     * @param <T>     T
     * @return T
     * @throws Exception 异常信息
     */
    public static <T> T toBean(String jsonStr, Class<T> clazz) throws Exception {
        return objectMapper.readValue(jsonStr, clazz);
    }

    /**
     * json 转map
     *
     * @param jsonStr jsonStr
     * @return map
     * @throws Exception json异常
     */
    public static Map<?, ?> toMap(String jsonStr) throws Exception {
        if (jsonStr != null && !"".equals(jsonStr)) {
            return objectMapper.readValue(jsonStr, Map.class);
        } else {
            return null;
        }
    }

    /**
     * json string 转 map with javaBean
     *
     * @param jsonStr jsonStr
     * @param clazz   class
     * @param <T>     T
     * @return Map<String, T>
     * @throws Exception json异常信息
     */
    public static <T> Map<String, T> toMapBean(String jsonStr, Class<T> clazz) throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr, new TypeReference<Map<String, Map<String, Object>>>() {
        });
        Map<String, T> result = Maps.newHashMap();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), mapToBean(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json 转数组
     *
     * @param jsonArrayStr jsonArrayStr
     * @param clazz        class
     * @param <T>          T
     * @return List<T>
     * @throws Exception json exception
     */
    public static <T> List<T> toList(String jsonArrayStr, Class<T> clazz) throws Exception {
        List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr, new TypeReference<List<Map<String, Object>>>() {
        });
        List<T> result = Lists.newArrayList();
        for (Map<String, Object> map : list) {
            result.add(mapToBean(map, clazz));
        }
        return result;
    }

    /**
     * map 转 bean
     *
     * @param map   map
     * @param clazz class
     * @param <T>   T
     * @return T
     */
    public static <T> T mapToBean(Map<?, ?> map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * @param value    value
     * @param callback callback
     * @return MappingJacksonValue
     */
    public static MappingJacksonValue jsonp(Object value, Class<?> callback) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(value);
        mappingJacksonValue.setSerializationView(callback);
        return mappingJacksonValue;
    }


    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr))
            return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '"':
                    if (last != '\\'){
                        isInQuotationMarks = !isInQuotationMarks;
                    }
                    sb.append(current);
                    break;
                case '{':
                case '[':
                    sb.append(current);
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent++;
                        addIndentBlank(sb, indent);
                    }
                    break;
                case '}':
                case ']':
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent--;
                        addIndentBlank(sb, indent);
                    }
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     * @author lizhgb
     * @Date 2015-10-14 上午10:38:04
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

}
