package com.atguigu.singleton.type6;

public class SingletonTest06 {

	public static void main(String[] args) {
		System.out.println("˫�ؼ��");
		Singleton instance = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		System.out.println(instance == instance2); // true
		System.out.println("instance.hashCode=" + instance.hashCode());
		System.out.println("instance2.hashCode=" + instance2.hashCode());
	}

}

// ����ʽ(�̰߳�ȫ��ͬ������)
class Singleton {
	// ע�� volatile !!!!
	private static volatile Singleton instance; // ע�� volatile !!!!

	private Singleton() {
	}

	// �ṩһ����̬�Ĺ��з���������˫�ؼ����룬����̰߳�ȫ����, ͬʱ�������������
	// ͬʱ��֤��Ч��, �Ƽ�ʹ��
	// DCL(double check lock ˫�˼�������)
	public static Singleton getInstance() { // ���ü� synchronized
		if (instance == null) {
			synchronized (Singleton.class) {
				if (instance == null) {
					instance = new Singleton();
				}
			}

		}
		return instance;
	}
}