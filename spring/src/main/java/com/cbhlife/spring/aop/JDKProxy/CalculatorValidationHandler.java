package com.cbhlife.spring.aop.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class CalculatorValidationHandler implements InvocationHandler {

    private Object target;

    public CalculatorValidationHandler(Object target) {
        this.target = target;
    }

    public static Object createProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new CalculatorValidationHandler(target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method " + method.getName() + " validation with args:" + Arrays.toString(args));
        for (Object arg : args) validate((Integer) arg);
        Object result = method.invoke(target, args);
        return result;
    }

    private void validate(Integer num) {
        if (num < 0) {
            throw new IllegalArgumentException("Postitive numbers only");
        }
    }
}
