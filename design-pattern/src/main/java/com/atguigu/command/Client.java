package com.atguigu.command;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//ʹ���������ģʽ�����ͨ��ң�������Ե�ƵĲ���
		
		//������ƵĶ���(������)
		LightReceiver lightReceiver = new LightReceiver();
		
		//���������صĿ�������
		LightOnCommand lightOnCommand = new LightOnCommand(lightReceiver);
		LightOffCommand lightOffCommand = new LightOffCommand(lightReceiver);
		
		//��Ҫһ��ң����
		RemoteController remoteController = new RemoteController();
		
		//�����ǵ�ң������������, ���� no = 0 �ǵ�ƵĿ��͹صĲ���
		remoteController.setCommand(0, lightOnCommand, lightOffCommand);
		
		System.out.println("--------���µƵĿ���ť-----------");
		remoteController.onButtonWasPushed(0);
		System.out.println("--------���µƵĹذ�ť-----------");
		remoteController.offButtonWasPushed(0);
		System.out.println("--------���³�����ť-----------");
		remoteController.undoButtonWasPushed();
		
		System.out.println("\n=========ʹ��ң�����������ӻ�==========\n");
		
		TVReceiver tvReceiver = new TVReceiver();
		
		TVOnCommand tvOnCommand = new TVOnCommand(tvReceiver);
		TVOffCommand tvOffCommand = new TVOffCommand(tvReceiver);

		//�����ǵ�ң������������, ���� no = 1 �ǵ��ӻ��Ŀ��͹صĲ���
		remoteController.setCommand(1, tvOnCommand, tvOffCommand);
		
		System.out.println("--------���µ��ӻ��Ŀ���ť-----------");
		remoteController.onButtonWasPushed(1);
		System.out.println("--------���µ��ӻ��Ĺذ�ť-----------");
		remoteController.offButtonWasPushed(1);
		System.out.println("--------���³�����ť-----------");
		remoteController.undoButtonWasPushed();

	}

}
