package proxy.cglib.aop.impl;

import proxy.cglib.aop.MethodAdvice;

import java.lang.reflect.Method;

/**
 * Created by guiqi on 2017/9/6.
 */
public class DefaultMethodAdvice implements MethodAdvice {

    @Override
    public void before(Object source, Object[] args, Method targetMethod) {
        System.out.println("前置通知:"+targetMethod.getName());
    }

    @Override
    public void afterReturning(Object source,Object result, Object[] args, Method targetMethod) {
        System.out.println("返回通知:"+result);
    }

    @Override
    public void throwing(Object source, Object[] args, Throwable e,
                         Method targetMethod) {
        System.out.println("异常通知:"+e.getMessage());
    }


    @Override
    public void after(Object source, Object result, Object[] args, Throwable e,
                      Method targetMethod) {
        System.out.println("后置通知:");
        System.out.println("返回:"+result);
        System.out.println("后置异常:"+e);
    }

}
