package program.tiger.sword.common;

import program.tiger.sword.common.utils.JsonUtil;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * @author junhu.li
 * @ClassName Test
 * @Description TODO
 * @date 2020/2/2919:40
 * @Version 1.0.0
 */
public class Test {
    private int a = 0;

    public static class A<T> {
        private String name;
        private Boolean using;
        protected T data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getUsing() {
            return using;
        }

        public void setUsing(Boolean using) {
            this.using = using;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    public static class B extends A<Map<String, Object>> {
        private Map<String, Object> data;

        @Override
        public Map<String, Object> getData() {
            return data;
        }

        @Override
        public void setData(Map<String, Object> data) {
            this.data = data;
        }
    }

    public static void main(String[] args) throws Exception {
        String json = "{\"name\":\"test\",\"using\":true,\"data\":{\"a\":\"a\",\"b\":1}}";
        B b = JsonUtil.toBean(json, B.class);
        System.out.println(b);
    }


    public Integer returnFinally() {
        try {
            a = 33;
            return a;
        } finally {
            a = 44;
        }
    }
}

