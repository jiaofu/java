package com.shui.javabase.cglib.ProxyInstance;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello Wolrd");
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		UserServiceProxy service=new UserServiceImpl();
		MyInvocationHandler handler=new MyInvocationHandler(service);
		UserServiceProxy proxy=(UserServiceProxy) handler.getProxy();
		proxy.add();
	}

}
