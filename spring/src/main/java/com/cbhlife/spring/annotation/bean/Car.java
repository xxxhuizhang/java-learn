package com.cbhlife.spring.annotation.bean;

import org.springframework.stereotype.Component;

@Component
public class Car {
	
	private String name;
	
	public Car(){
		System.out.println("car constructor...");
	}
	
	public void init(){
		System.out.println("car ... init...");
	}
	
	public void detory(){
		System.out.println("car ... detory...");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("---设置属性");
		this.name = name;
	}
	
	

}
