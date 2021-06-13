package program.tiger.sword.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import program.tiger.sword.common.response.LeonidResponse;

import java.util.List;

/**
 * @author junhu.li
 * @ClassName MvcProj
 * @Description TODO
 * @date 2020/3/3115:20
 * @Version 1.0.0
 */
@Getter
@Setter
public class MvcProj {
    @JsonProperty(value = "asset_key")
    private String assetKey;
    @JsonProperty(value = "project_id")
    private String id;
    private String name;


    @Getter
    @Setter
    public static class Response extends LeonidResponse<List<MvcProj>> {
        private List<MvcProj> result;
    }
}
