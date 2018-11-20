package program.tiger.sword.common.dp.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import program.tiger.sword.common.dp.annotation.Read;
import program.tiger.sword.common.dp.datasource.ConnectionType;
import program.tiger.sword.common.dp.datasource.ConnectionTypeHolder;

/**
 * 路由切面
 *
 * @author TigerLee
 * @date 2018年11月16日17:35:28
 */
@Aspect
@Order(0)
@Slf4j
public class RoutingAop {

    @Pointcut("@annotation(program.tiger.sword.common.dp.annotation.Read)")
    public void readPointcut() {
    }

    @Around(value = "readPointcut()&&@annotation(read)", argNames = "pjp,read")
    public Object aroundRead(ProceedingJoinPoint pjp, Read read) throws Throwable {

        ConnectionType origType = ConnectionTypeHolder.get();
        try {
            ConnectionType newType = new ConnectionType(ConnectionType.READ, read.value());
            ConnectionTypeHolder.set(newType);
            return pjp.proceed();
        } catch (Throwable throwable) {
            log.error("error while processing read method", throwable);
            throw throwable;
        } finally {
            if (origType != null) {
                ConnectionTypeHolder.set(origType);
            } else {
                ConnectionTypeHolder.release();
            }
        }
    }

    @Pointcut("@annotation(program.tiger.sword.common.dp.annotation.Write)")
    public void writePointcut() {
    }

    @Around("writePointcut()")
    public Object aroundWrite(ProceedingJoinPoint pjp) throws Throwable {
        ConnectionType origType = ConnectionTypeHolder.get();
        try {
            ConnectionType newType = new ConnectionType(ConnectionType.READ_WRITE, null);
            ConnectionTypeHolder.set(newType);
            return pjp.proceed();
        } catch (Throwable throwable) {
            log.warn("error while processing write method", throwable);
            throw throwable;
        } finally {
            if (origType != null) {
                ConnectionTypeHolder.set(origType);
            } else {
                ConnectionTypeHolder.release();
            }
        }
    }

    public Object aroundTransactional(ProceedingJoinPoint pjp) throws Throwable {
        return aroundWrite(pjp);
    }

}
