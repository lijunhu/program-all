package program.tiger.sword.http;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.URLEncoder;

/**
 * @author junhu.li
 * @ClassName Response
 * @Description TODO
 * @date 2019-06-1817:58
 * @Version 1.0.0
 */
public class Response {
    private int code;
    private String msg;
    private Object result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static class PageResponse {
        private Object pageData;
        private int totalCount;

        public Object getPageData() {
            return pageData;
        }

        public void setPageData(Object pageData) {
            this.pageData = pageData;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }


    public static void main(String[] args) throws Exception{
        String test = URLEncoder.encode("test aa 李俊虎 - _ . * () {} [] & * ^ %ADSADAA","UTF-8");
        System.out.println(test);
    }
}



