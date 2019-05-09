package com.shui.javabase.cglib.cglibtext;


import org.springframework.cglib.core.DebuggingClassWriter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Downloads");
        UserServiceImpl service=new UserServiceImpl();
        CglibProxy cP=new CglibProxy();
        UserService proxy=(UserService)cP.getProxy(service.getClass());
        proxy.add();
     

        System.out.println( "Hello World!" );
    }
}
