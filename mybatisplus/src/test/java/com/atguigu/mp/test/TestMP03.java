package com.atguigu.mp.test;

import com.atguigu.mp.beans.Employee;
import com.atguigu.mp.mapper.EmployeeMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestMP03 {
	
	ApplicationContext ctx  = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	EmployeeMapper employeeMapper = ctx.getBean("employeeMapper",EmployeeMapper.class);
	
	/**
	 * 测试 乐观锁插件
	 */
	
	@Test
	public void testOptimisticLocker() {
		//更新操作
		Employee employee = new Employee();
		employee.setId(15);
		employee.setLastName("TomAA");
		employee.setEmail("tomAA@sina.com");
		employee.setGender(1);
		employee.setAge(22);
		employee.setVersion(3);
		
		employeeMapper.updateById(employee);
		
	}
	
	/**
	 * 测试 性能分析插件
	 */
	@Test
	public void testPerformance() {
		Employee employee = new Employee();
		employee.setLastName("玛利亚老师");
		employee.setEmail("mly@sina.com");
		employee.setGender(0);
		employee.setAge(22);
		
		employeeMapper.insert(employee);
	}
	
	/**
	 * 测试SQL执行分析插件
	 */
	@Test
	public void testSQLExplain() {
		//Full table operation is prohibited. SQL: DELETE FROM tbl_employee
		//xml里的分析插件SqlExplainInterceptor
		employeeMapper.delete(null);  // 全表删除
	}
	
	/**
	 * 测试分页插件
	 */
	@Test
	public void testPage() {
		
		Page<Employee> page = new Page<>(2,2);
		List<Employee > emps = employeeMapper.selectPage(page, null);
		System.out.println(emps);

		//将查询的结果封装到page对象中
		page.setRecords(emps);

		System.out.println("===============获取分页相关的一些信息======================");
		System.out.println("总条数:" +page.getTotal());
		System.out.println("当前页码: "+  page.getCurrent());
		System.out.println("总页码:" + page.getPages());
		System.out.println("每页显示的条数:" + page.getSize());
		System.out.println("是否有上一页: " + page.hasPrevious());
		System.out.println("是否有下一页: " + page.hasNext());
		System.out.println("records: " + page.getRecords());


	}
		
}
