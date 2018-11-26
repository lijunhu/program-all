package program.tiger.sword.enums;

/**
 * 参数检查枚举
 *
 * @author TigerLee
 * @date 2018年11月26日21:13:21
 */
public enum ParamCheckEnum {
    SUCCESS(000000, "处理成功"),
    UNKNOWN_ERROR(999999, "未知异常");

    private Integer code;
    private String codeDesc;

    ParamCheckEnum(Integer code, String codeDesc) {
        this.code = code;
        this.codeDesc = codeDesc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }
}
