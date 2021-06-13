package program.tiger.sword.common.response;

import lombok.*;

/**
 * @author junhu.liz
 * @ClassName LeonidResponse
 * @Description TODO
 * @date 2019-08-1211:03
 * @Version 1.0.0
 */

@Setter
@Getter
public class LeonidResponse <T> {
    private Integer code;
    private String msg;
    private T result;
    private Integer total;

    public LeonidResponse() {

    }
}
