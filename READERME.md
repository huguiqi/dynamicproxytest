# 动态代理方式
## JDK自带动态代理
JDK自带的动态代理方式必须要被代理对象实现某些接口；

JDK动态代理所用到的代理类在程序调用到代理类对象时才由JVM真正创建，JVM根据传进来的 业务实现类对象 以及 方法名 ，动态地创建了一个代理类的class文件并被字节码引擎执行，然后通过该代理类对象进行方法调用。我们需要做的，只需指定代理类的预处理、调用后操作即可。


## 关键代码

1. 实现InvocationHandler接口
2. 调用java.lang.reflect.Proxy类的newProxyInstance或者getProxyClass方法

将被代理类传给即将生成的代理类
Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);

3. 重载invoke方法，将要处理的统一事件填入
    
    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {.....}

## 关键源码分析
    
    newProxyInstance ---> getProxyClass0 --->proxyClassCache.get(loader, interfaces)--->Object subKey = Objects.requireNonNull(subKeyFactory.apply(key, parameter));---->ProxyClassFactory.apply 

## apply方法里关键代码：

//生成代理类class文件
    
    long num = nextUniqueNumber.getAndIncrement();     
    String proxyName = proxyPkg + proxyClassNamePrefix + num; 
    byte[] proxyClassFile = ProxyGenerator.generateProxyClass(proxyName, interfaces, accessFlags);


## CGLIB

cglib是针对类来实现代理的，原理是对指定的业务类生成一个子类，并覆盖其中业务方法实现代理。因为采用的是继承，所以不能对final修饰的类进行代理。

