package service;

import proxy.CarModel;

/**
 * Created by guiqi on 2017/9/4.
 */
public class AudiCar {

    private String name;

    public AudiCar(){
        super();
    }

    public AudiCar(String name) {
        this.name = name;
    }

    public void start() {
        System.out.println(name+"-------start");
    }

    public void run() {
        System.out.println(name+"--------run");
    }


    public String getName() {
        System.out.println("调用了get方法");
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
