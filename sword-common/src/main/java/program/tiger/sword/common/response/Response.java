package program.tiger.sword.common.response;

import java.io.Serializable;

/**
 * 返回响应结果基类
 *
 * @date 2018年11月26日21:08:25
 * @2018年11月26日21:08:28
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer rspCode;
    private String rspMsg;
    private T data;

    public Response() {
    }

    public Integer getRspCode() {
        return rspCode;
    }

    public void setRspCode(Integer rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
