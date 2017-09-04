package service;

import proxy.CarModel;

/**
 * Created by guiqi on 2017/9/4.
 */
public class BENZCar implements CarModel {

    private String name;

    public BENZCar(String name) {
        this.name = name;
    }

    public void start() {
        System.out.println(name+"start");
    }

    public void run() {
        System.out.println(name+"run");
    }

    public void pause() {
        System.out.println(name+"pause");
    }

    public void stop() {
        System.out.println(name+"stop");
    }

}
