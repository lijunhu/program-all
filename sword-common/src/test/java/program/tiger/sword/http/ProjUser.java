package program.tiger.sword.http;

import lombok.Getter;
import lombok.Setter;
import program.tiger.sword.common.response.LeonidResponse;

import java.util.List;

/**
 * @author junhu.li
 * @ClassName ProjUser
 * @Description TODO
 * @date 2020/3/3116:29
 * @Version 1.0.0
 */
@Getter
@Setter
public class ProjUser {
        private String user ;

        @Setter
        @Getter
        public static class Response extends LeonidResponse<List<ProjUser>>{
            private List<ProjUser> result;
        }
}
