package com.cbhlife.spring;

import java.util.Map;

import com.cbhlife.spring.annotation.bean.Person;
import com.cbhlife.spring.annotation.config.MainConfig;
import com.cbhlife.spring.annotation.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;


public class IOCTest {
	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

	@Test
	public void testImport() {
		System.out.println("------------------testImport");
		printBeans(applicationContext);
		System.out.println("------------------testImport end");
//		Blue bean = applicationContext.getBean(Blue.class);
//		System.out.println(bean);

		// 工厂Bean获取的是调用getObject创建的对象
		Object bean2 = applicationContext.getBean("colorFactoryBean");
		Object bean3 = applicationContext.getBean("colorFactoryBean");
		System.out.println("bean的类型：" + bean2.getClass());
		System.out.println(bean2 == bean3);

		Object bean4 = applicationContext.getBean("&colorFactoryBean");
		System.out.println(bean4.getClass() + " " + (bean3 == bean4));
	}

	private void printBeans(AnnotationConfigApplicationContext applicationContext) {
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
		for (String name : definitionNames) {
			System.out.println(name);
		}
	}

	@SuppressWarnings("resource")
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig.class);
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
		for (String name : definitionNames) {
			System.out.println(name);
		}
	}
	
	@Test
	public void test02() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		
		System.out.println("ioc容器创建完成....");
		Object bean = applicationContext.getBean("person");
		Object bean2 = applicationContext.getBean("person");
		System.out.println(bean == bean2);
	}

	@Test
	public void test03() {
		String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		// 动态获取环境变量的值；Windows 10
//		String property = environment.getProperty("os.name");
//		System.out.println(property);
		for (String name : namesForType) {
			System.out.println(name);
		}
		
		System.out.println("------------------------------");

		Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
		System.out.println(persons);

	}

}
