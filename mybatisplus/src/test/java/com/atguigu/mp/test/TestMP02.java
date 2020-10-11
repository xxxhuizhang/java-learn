package com.atguigu.mp.test;

import com.atguigu.mp.beans.Employee;
import com.atguigu.mp.beans.User;
import com.atguigu.mp.mapper.EmployeeMapper;
import com.atguigu.mp.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestMP02 {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    EmployeeMapper employeeMapper = ctx.getBean("employeeMapper", EmployeeMapper.class);
    UserMapper userMapper = ctx.getBean("userMapper", UserMapper.class);


    /**
     * 分页插件
     */
    @Test
    public void testSelectPage() {
        //5. 分页查询
        Page<Employee> page = employeeMapper.selectPage(new Page<>(1, 3), null);

//        Page<Employee> page = employeeMapper.selectPage(
//                new Page<>(1, 2),
//                new QueryWrapper<Employee>()
//                        .between("age", 18, 50)
//                        .eq("gender", 1)
//                        .eq("last_name", "Tom")
//        );

        System.out.println("===============获取分页相关的一些信息======================");
        System.out.println("总条数:" + page.getTotal());
        System.out.println("当前页码: " + page.getCurrent());
        System.out.println("总页码:" + page.getPages());
        System.out.println("每页显示的条数:" + page.getSize());
        System.out.println("是否有上一页: " + page.hasPrevious());
        System.out.println("是否有下一页: " + page.hasNext());
        System.out.println("records: " + page.getRecords());

    }

    /**
     * 乐观锁插件
     */

    @Test
    public void testOptimisticLocker() {
        //更新操作
        Employee employee = new Employee();
        employee.setId(15L);
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
     * 测试自定义全局操作
     */
    @Test
    public void testMySqlInjector() {
//		Integer result = employeeMapper.deleteAll();
//		System.out.println("result: " +result );
    }


    /**
     * 测试Oracle 主键 Sequence
     */
    @Test
    public void testOracle() {
        User user = new User();
        user.setLogicFlag(1);
        user.setName("OracleSEQ");
        userMapper.insert(user);
    }


}
