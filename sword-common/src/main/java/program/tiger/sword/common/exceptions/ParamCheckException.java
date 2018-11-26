package program.tiger.sword.common.exceptions;

import program.tiger.sword.enums.ParamCheckEnum;

/**
 * 参数检查异常
 *
 * @author TigerLee
 * @date 2018年11月26日21:10:42
 */
public class ParamCheckException extends RuntimeException {


    private ParamCheckEnum paramCheckEnum;

    public ParamCheckException(ParamCheckEnum paramCheckEnum) {
        super(paramCheckEnum.getCodeDesc());
        this.paramCheckEnum = paramCheckEnum;
    }

    public ParamCheckException(ParamCheckEnum paramCheckEnum, String message) {
        super(message);
        this.paramCheckEnum = paramCheckEnum;
    }

    public ParamCheckException(ParamCheckEnum paramCheckEnum, String message, Throwable cause) {
        super(message, cause);
        this.paramCheckEnum = paramCheckEnum;
    }

    public ParamCheckException(ParamCheckEnum paramCheckEnum, Throwable cause) {
        super(cause);
        this.paramCheckEnum = paramCheckEnum;
    }

    public ParamCheckEnum getCodeMsg() {
        return paramCheckEnum;
    }

    public void setCodeMsg(ParamCheckEnum paramCheckEnum) {
        this.paramCheckEnum = paramCheckEnum;
    }
}
