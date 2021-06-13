package program.tiger.sword.enums;

import lombok.Getter;

/**
 * 参数检查枚举
 *
 * @author TigerLee
 * @date 2018年11月26日21:13:21
 */
public enum ParamCheckEnum {
    /**
     * 校验成功
     */
    SUCCESS(000000, "处理成功"),
    /**
     * 异常错误信息
     */
    UNKNOWN_ERROR(999999, "未知异常");

    /**
     * 参数校验返回错误码
     */
    private Integer code;
    /**
     * 描述信息
     */
    private String codeDesc;

    /**
     * 构造错误信息
     *
     * @param code
     * @param codeDesc
     */
    ParamCheckEnum(Integer code, String codeDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
    }

    public Integer getCode() {
        return code;
    }

    public String getCodeDesc() {
        return codeDesc;
    }
}
