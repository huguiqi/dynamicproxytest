package proxy.cglib;

import net.sf.cglib.proxy.*;
import service.AudiCar;

import java.lang.reflect.Method;

/**
 * Created by guiqi on 2017/9/4.
 */
public class DynamicDriverCglib2 implements MethodInterceptor{

    private Object target;


    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        System.out.println(method.getName() +"日志记录开始——————");
        result = methodProxy.invokeSuper(o,objects);//调用业务类（父类中）的方法
        System.out.println(method.getName() +"日志记录结束——————");
        return result;
    }

    public static void main(String[] args) {
        DynamicDriverCglib2 driver = new DynamicDriverCglib2();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(AudiCar.class);
        //设置两个回调用自定义CarFilter类来决定调用哪个回调处理
        enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE,driver});
        enhancer.setCallbackFilter(new CarFilter());
        AudiCar audiCar = (AudiCar) enhancer.create(new Class[]{String.class},new Object[]{"AUDI"});
        audiCar.start();
        audiCar.run();

    }

}


class CarFilter implements CallbackFilter{

    /**
     * 返回1使用DynamicDriverCglib2类生成的代理类调用被代理类方法
     * 返回0代表使用执行父类方法,不使用代理类处理
     * @param method
     * @return
     */
    public int accept(Method method) {
        if (method.getName().equals("start")){
            return 1;
        }
        return 0;
    }
}




