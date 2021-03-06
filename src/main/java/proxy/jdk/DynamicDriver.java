package proxy.jdk;



import proxy.CarModel;
import service.BmwCar;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by guiqi on 2017/9/4.
 * 这是jdk动态代理
 */
public class DynamicDriver implements InvocationHandler{

    private Object carModel;

    /**
     * 绑定并返回代理类
     * @param target
     * @return
     */
    public Object getInstance(Object target){
        this.carModel = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }



    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result=null;
        System.out.println(method.getName() +"日志记录开始——————");
        //调用真正的业务方法
        result=method.invoke(carModel, args);
        System.out.println(method.getName() +"日志记录结束——————");
        return result;
    }


    public static void main(String[] args) {
        DynamicDriver driver = new DynamicDriver();
        CarModel carModel =(CarModel) driver.getInstance(new BmwCar("BMW"));
        carModel.start();
        carModel.run();
    }
}
