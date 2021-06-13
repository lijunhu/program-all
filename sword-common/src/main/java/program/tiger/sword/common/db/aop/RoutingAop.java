package program.tiger.sword.common.db.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import program.tiger.sword.common.db.annotation.Read;
import program.tiger.sword.common.db.datasource.ConnectionType;
import program.tiger.sword.common.db.datasource.ConnectionTypeHolder;

/**
 * 路由切面
 *
 * @author TigerLee
 * @date 2018年11月16日17:35:28
 */
@Aspect
@Order(0)
public class RoutingAop {

    @Pointcut("@annotation(program.tiger.sword.common.db.annotation.Read)")
    public void readPointcut() {
    }

    @Around(value = "readPointcut()&&@annotation(read)", argNames = "pjp,read")
    public Object aroundRead(ProceedingJoinPoint pjp, Read read) throws Throwable {

        ConnectionType origType = ConnectionTypeHolder.get();
        try {
            ConnectionType newType = new ConnectionType(ConnectionType.READ, read.value());
            ConnectionTypeHolder.set(newType);
            return pjp.proceed();
        } finally {
            if (origType != null) {
                ConnectionTypeHolder.set(origType);
            } else {
                ConnectionTypeHolder.release();
            }
        }
    }

    @Pointcut("@annotation(program.tiger.sword.common.db.annotation.Write)")
    public void writePointcut() {
    }

    @Around("writePointcut()")
    public Object aroundWrite(ProceedingJoinPoint pjp) throws Throwable {
        ConnectionType origType = ConnectionTypeHolder.get();
        try {
            ConnectionType newType = new ConnectionType(ConnectionType.READ_WRITE, null);
            ConnectionTypeHolder.set(newType);
            return pjp.proceed();
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
