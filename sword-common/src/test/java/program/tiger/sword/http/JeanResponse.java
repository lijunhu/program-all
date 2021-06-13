package program.tiger.sword.http;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author junhu.li
 * @ClassName JeanResponse
 * @Description TODO
 * @date 2019-10-3020:07
 * @Version 1.0.0
 */
@Getter
@Setter
public class JeanResponse {
 private int code;
 private String msg;
 private Object data;


    @Setter
    @Getter
    public static class AppInfoResponse extends JeanResponse{
        private List<AppInfo> data;
    }

    @Getter
    @Setter
    public static class AppInfo{
        private String name;
        private String uk;
    }
}


