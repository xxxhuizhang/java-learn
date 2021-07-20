package com.cbhlife.spring.aop.JDKProxy;

public class Main {
    public static void main(String[] args) {

        ArithmeticCalculator calculatoralculator = new ArithmeticCalculatorImpl();

        ArithmeticCalculator calculatorProxy =
                (ArithmeticCalculator) CalculatorValidationHandler
                        .createProxy(CalculatorLoggingHandler.createProxy(calculatoralculator));

        System.out.println(calculatorProxy.add(1, 2));

    }
}

