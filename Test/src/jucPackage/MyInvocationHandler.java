package jucPackage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {

	private Object target;
	 public MyInvocationHandler(Object target) {
		// TODO Auto-generated constructor stub
		super();
		this.target=target;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
       // PerformanceMonior.begin(target.getClass().getName()+"."+method.getName());
		System.out.println("-----------------begin "+method.getName()+"-----------------");
        Object result=method.invoke(target, args);
        System.out.println("-----------------end "+method.getName()+"-----------------");
        return result;
	
	}
	public Object getProxy() {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
	}

}
