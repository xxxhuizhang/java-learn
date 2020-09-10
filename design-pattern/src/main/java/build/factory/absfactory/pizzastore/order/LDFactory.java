package build.factory.absfactory.pizzastore.order;


import build.factory.absfactory.pizzastore.pizza.LDCheesePizza;
import build.factory.absfactory.pizzastore.pizza.LDPepperPizza;
import build.factory.absfactory.pizzastore.pizza.Pizza;


public class LDFactory implements AbsFactory {

	@Override
	public Pizza createPizza(String orderType) {
		System.out.println("~使用的是抽象工厂模式~");
		Pizza pizza = null;
		if (orderType.equals("cheese")) {
			pizza = new LDCheesePizza();
		} else if (orderType.equals("pepper")) {
			pizza = new LDPepperPizza();
		}
		return pizza;
	}

}
