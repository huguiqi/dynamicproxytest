package proxy.cglib.aop;

import net.sf.cglib.proxy.LazyLoader;

/**
 * Created by guiqi on 2017/9/6.
 */
//模拟hibernate延迟加载效果
public class ConcreteClassLazyLoader<CreateSate> implements LazyLoader {
    //懒加载在对象实际使用时只创建一次，此对象作为懒加载标识
    private CreateSate p;
    public ConcreteClassLazyLoader(CreateSate p) {
        this.p=p;
    }
    @Override
    public Object loadObject() throws Exception {
        System.err.println("调用同一对象的任何方法只输出一次..");
        return p;
    }

}
