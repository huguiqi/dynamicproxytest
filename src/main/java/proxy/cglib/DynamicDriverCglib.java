package proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import service.AudiCar;

import java.lang.reflect.Method;

/**
 * Created by guiqi on 2017/9/4.
 */
public class DynamicDriverCglib implements MethodInterceptor{

    private Object target;

    public <T> T getInstance(Object target){
        this.target = target;  //给业务对象赋值
        Enhancer enhancer = new Enhancer(); //创建加强器，用来创建动态代理类
        enhancer.setSuperclass(this.target.getClass());  //为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
        //设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
        enhancer.setCallback(this);
        // 创建动态代理类对象并返回
        return (T)enhancer.create();
    }

    /**
     * 创建代理对象方法
     *
     * @param target        代理对象
     * @param args          对应的构造器参数类型
     *
     *                          例：有构造器如下
     *                          public Person(name,age){...} name为String.class age为int.class
     *                          写入name的类型与age的类型
     *
     *                          则：new Class[]{String.class,int.class}
     *
     * @param argsValue     对应的构造器参数值
     *
     *                          例:如此创建对象 new Person("name",23) 用以下方式传入：new Object[]{"name",23}
     *
     * @param <T>           <泛型方法>
     * @return              返回跟代理对象类型
     */
    public <T> T getInstance(Class clazz,Class[] args,Object[] argsValue){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return (T) enhancer.create(args,argsValue);
    }


    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        System.out.println(method.getName() +"日志记录开始——————");
        result = methodProxy.invokeSuper(o,objects);//调用业务类（父类中）的方法
        System.out.println(method.getName() +"日志记录结束——————");
        return result;
    }

    public static void main(String[] args) {
        DynamicDriverCglib driver = new DynamicDriverCglib();
        //生成了AudiCar的子类，将切面逻辑加入到
        AudiCar carModel =(AudiCar) driver.getInstance(AudiCar.class,new Class[]{String.class},new Object[]{"AUDI"});
        carModel.start();
        carModel.run();
    }

}
