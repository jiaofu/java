package com.shui.javabase.cglib.cglibtext;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

	private Enhancer enhancer=new Enhancer();
	
	public Object getProxy(Class clazz) {
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
      System.out.println("开始");
       Object result = proxy.invokeSuper(obj, args);
       System.out.println("结束");
   

        return result;
    }
}
