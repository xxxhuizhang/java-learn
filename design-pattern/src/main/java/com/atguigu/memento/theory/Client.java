package com.atguigu.memento.theory;

public class Client {

	/*
	 * 如果希望保存多个originator对象的不同时间的状态，也可以，只需要 要 HashMap <String, 集合>
	 * 
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Originator originator = new Originator();
		Caretaker caretaker = new Caretaker();

		originator.setState(" 状态#1 攻击力 100 ");
		caretaker.add(originator.saveStateMemento());// 保存了当前的状态

		originator.setState(" 状态#2 攻击力 80 ");
		caretaker.add(originator.saveStateMemento());

		originator.setState(" 状态#3 攻击力 50 ");
		caretaker.add(originator.saveStateMemento());

		System.out.println("当前的状态是 =" + originator.getState());

		// 希望得到状态 1, 将 originator 恢复到状态1

		originator.getStateFromMemento(caretaker.get(0));
		System.out.println("恢复到状态1 , 当前的状态是");
		System.out.println("当前的状态是 =" + originator.getState());

	}

}
