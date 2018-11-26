package program.tiger.sword.common.respone;

import lombok.Builder;

/**
 * 返回响应结果基类
 * @date 2018年11月26日21:08:25
 * @2018年11月26日21:08:28
 */
@Builder
public abstract class Respone<T> {

    private Integer rspCode;
    private String rspMsg;
}
