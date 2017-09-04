package proxy;


import service.AudiCar;
import service.BENZCar;
import service.BmwCar;

/**
 * Created by guiqi on 2017/9/4.
 * 这是静态代理，解决了不同实现的调用问题，可以统一给不同的实现添加日志
 */
public class DriverProxy {

    private CarModel carModel;

    public DriverProxy(String type) {
        if ("BMW".equals(type)){
            this.carModel = new BmwCar("宝马");
        }
        if ("BC".equals(type)){
            this.carModel = new BENZCar("奔弛");
        }
    }

    public void start(){
        System.out.println("==========start日志开始========");
        carModel.start();
        System.out.println("==========start日志结束========");
    }

    public void run(){
        System.out.println("==========run日志开始========");
        carModel.run();
        System.out.println("==========run日志结束========");
    }

    public void stop(){
        System.out.println("==========stop日志开始========");
        carModel.stop();
        System.out.println("==========stop日志结束========");
    }


    public static void main(String[] args) {
        DriverProxy driverProxy1 = new DriverProxy("BMW");
        driverProxy1.start();
        driverProxy1.run();
        driverProxy1.stop();
    }
}
