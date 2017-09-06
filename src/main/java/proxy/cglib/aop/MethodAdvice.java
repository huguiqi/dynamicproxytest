package proxy.cglib.aop;

import java.lang.reflect.Method;

/**
 * Created by guiqi on 2017/9/6.
 */
public interface MethodAdvice {
    void before(Object source,Object[] args,Method targetMethod);
    void after(Object source,Object result,Object[] args,Throwable e,Method targetMethod);
    void afterReturning(Object source,Object result,Object[] args,Method targetMethod);
    void throwing(Object source,Object[] args,Throwable e,Method targetMethod);
}
