package behaviour.memento.theory;

public class Client {

	/*
	 * ���ϣ��������originator����Ĳ�ͬʱ���״̬��Ҳ���ԣ�ֻ��Ҫ Ҫ HashMap <String, ����>
	 * 
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Originator originator = new Originator();
		Caretaker caretaker = new Caretaker();

		originator.setState(" ״̬#1 ������ 100 ");
		caretaker.add(originator.saveStateMemento());// �����˵�ǰ��״̬

		originator.setState(" ״̬#2 ������ 80 ");
		caretaker.add(originator.saveStateMemento());

		originator.setState(" ״̬#3 ������ 50 ");
		caretaker.add(originator.saveStateMemento());

		System.out.println("��ǰ��״̬�� =" + originator.getState());

		// ϣ���õ�״̬ 1, �� originator �ָ���״̬1

		originator.getStateFromMemento(caretaker.get(0));
		System.out.println("�ָ���״̬1 , ��ǰ��״̬��");
		System.out.println("��ǰ��״̬�� =" + originator.getState());

	}

}
