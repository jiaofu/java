package com.jex.market.dao.util.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Describe: 动态数据源代理类
 */
@Aspect
@Component
@Order(value = -1)
public class DynamicDataSourceAspect {

    @Before("@annotation(com.jex.market.dao.util.db.DBAnno)")
    public void beforeSwitchDS(JoinPoint point) {

        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();

        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);

            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DBAnno.class)) {
                DBAnno annotation = method.getAnnotation(DBAnno.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 切换数据源
        DataSourceContextHolder.setDataSource(dataSource);

    }

    @After("@annotation(com.jex.market.dao.util.db.DBAnno)")
    public void afterSwitchDS(JoinPoint point) {

        DataSourceContextHolder.clearDataSource();

    }
}
