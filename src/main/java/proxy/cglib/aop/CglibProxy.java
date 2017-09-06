package proxy.cglib.aop;

import net.sf.cglib.proxy.*;
import proxy.cglib.aop.impl.DefaultMethodAdvice;
import service.AudiCar;

import java.lang.reflect.Method;

/**
 * Created by guiqi on 2017/9/4.
 */
public class CglibProxy implements MethodInterceptor{

    private Class<?> target;
    //模拟spring通知 当然spring 使用的是单个方法 我为了方便使用一个实现
    private MethodAdvice advice = new DefaultMethodAdvice();

    @SuppressWarnings("unchecked")
    public <P> P newProxyInstance(Class clazz,Class[] args,Object[] argsValue) {
        this.target = clazz;
        Enhancer enhancer = new Enhancer();
        try {
            enhancer.setSuperclass(this.target);
            enhancer.setCallbacks(new Callback[]{this,new ConcreteClassLazyLoader(this.target.newInstance())});
            enhancer.setCallbackFilter(new CallbackFilter() {
                private static final int LAZY_LOADER=1;//0代表Callbacks所对应的数组下标【Proxy】
                private static final int ADVICE=0;//1代表Callbacks所对应的数组下标【ConcreteClassLazyLoader】
                @Override
                public int accept(Method method) {
                    if(method.getName().startsWith("get")){//模拟懒加载(单例)
                        return LAZY_LOADER;//
                    }
                    return ADVICE;//模拟拦截器通知
                }
            });
            return (P)enhancer.create(args,argsValue);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAdvice(MethodAdvice advice) {
        this.advice = advice;
    }
    //环绕通知自己去尝试吧在此不赘述了
    @Override
    public Object intercept(Object proxy, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        Object result = null;
        String name = method.getName();
        String gname = method.toGenericString();
        if (!gname.contains("java.lang.Object." + name + "()")) {//对象默认方法不处理
            advice.before(proxy, args, method);//spring 前置通知
            Exception exception = null;
            try {
                // 调用目标方法，用methodProxy,
                // 而不是原始的method，以提高性能
                result = methodProxy.invokeSuper(proxy, args);
                advice.afterReturning(proxy, result, args, method);//spring 返回通知
            } catch (Exception e) {
                advice.throwing(proxy, args, e, method);//spring 异常通知
                exception = e;
            }
            advice.after(proxy, result, args, exception, method);//spring 前置通知
            if (exception != null)
                throw exception;
            // 调用之后
        }
        return result;
    }


    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        //生成了AudiCar的子类，将切面逻辑加入到
        AudiCar carModel =(AudiCar) proxy.newProxyInstance(AudiCar.class,new Class[]{String.class},new Object[]{"AUDI"});
        carModel.getName();
        carModel.start();
        carModel.run();
        carModel.getName();
    }
}


