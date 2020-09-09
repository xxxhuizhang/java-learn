package com.atguigu.iterator;

import java.util.Iterator;
import java.util.List;

public class InfoColleageIterator implements Iterator<Department> {

	List<Department> departmentList; // ��Ϣ����ѧԺ����List��ʽ���ϵ
	int index = -1;// ����

	public InfoColleageIterator(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	// �ж�list�л���û����һ��Ԫ��
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if (index >= departmentList.size() - 1) {
			return false;
		} else {
			index += 1;
			return true;
		}
	}

	@Override
	public Department next() {
		// TODO Auto-generated method stub
		return departmentList.get(index);
	}

	// ��ʵ��remove
	public void remove() {

	}

}
