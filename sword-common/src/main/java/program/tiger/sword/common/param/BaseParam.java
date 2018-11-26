package program.tiger.sword.common.param;

import program.tiger.sword.common.exceptions.ParamCheckException;

/**
 * 参数基类
 *
 * @author TigerLee
 * @date 2018年11月26日21:07:33
 */
public abstract class BaseParam {
    abstract void paramCheck() throws ParamCheckException;
}
