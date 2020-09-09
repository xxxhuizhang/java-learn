package com.atguigu.mediator.smarthouse;

//ͬ�³�����
public abstract class Colleague {

	private Mediator mediator;
	public String name;

	public Colleague(Mediator mediator, String name) {
		this.mediator = mediator;
		this.name = name;
	}
	
	public abstract void SendMessage(int stateChange);

	public Mediator getMediator() {
		return this.mediator;
	}
	
}
