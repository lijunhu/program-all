package program.tiger.sword.http;

import lombok.Getter;
import lombok.Setter;
import program.tiger.sword.common.response.LeonidResponse;

/**
 * @author junhu.li
 * @ClassName LeonidUser
 * @Description TODO
 * @date 2020/3/3116:04
 * @Version 1.0.0
 */
@Getter
@Setter
public class LeonidUser {


    private String userId;
    private String name;

    @Setter
    @Getter
    public static class Response extends LeonidResponse<String>{
        String result;
    }
}
