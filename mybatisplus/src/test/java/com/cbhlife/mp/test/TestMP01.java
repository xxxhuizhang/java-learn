package com.cbhlife.mp.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cbhlife.mp.beans.Employee;
import com.cbhlife.mp.mapper.EmployeeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

public class TestMP01 {

    private ApplicationContext ioc =
            new ClassPathXmlApplicationContext("applicationContext.xml");

    private EmployeeMapper employeeMapper =
            ioc.getBean("employeeMapper", EmployeeMapper.class);

    /**
     * 条件构造器  删除操作
     */
    @Test
    public void testEntityWrapperDelete() {

        employeeMapper.delete(
                new QueryWrapper<Employee>()
                        .eq("last_name", "Tom")
                        .eq("age", 22)
        );

        employeeMapper.delete(
                new LambdaQueryWrapper<Employee>()
                        .eq(Employee::getLastName, "Tom")
                        .eq(Employee::getAge, 22)
        );
        
    }


    /**
     * 条件构造器  修改操作
     */
    @Test
    public void testEntityWrapperUpdate() {

        Employee employee = new Employee();
        employee.setLastName("苍老师");
        employee.setEmail("cls@sina.com");
        employee.setGender(0);


        employeeMapper.update(employee,
                new QueryWrapper<Employee>()
                        .eq("last_name", "Tom")
                        .eq("age", 44)
        );
    }

    /**
     * 条件构造器   查询操作
     */
    @Test
    public void testEntityWrapperSelect() {

        // 查询tbl_employee表中， 性别为女并且名字中带有"老师" 或者  邮箱中带有"a"
//        List<Employee> emps = employeeMapper.selectList(
//                new QueryWrapper<Employee>()
//                        .eq("gender", 0)/**/
//                        .like("last_name", "a")
//                        .or().like("email", "a")    // SQL: (gender = ? AND last_name LIKE ? OR email LIKE ?)
//                        .or(i -> i.like("email", "b")) // SQL: (gender = ? AND last_name LIKE ?) OR (email LIKE ?)
//        );
//        System.out.println(emps);

        // 查询性别为女的, 根据age进行排序(asc/desc), 简单分页
        List<Employee> emps = employeeMapper.selectList(
                new QueryWrapper<Employee>()
                        .eq("gender", 0)
                        .orderByAsc("age")
                        //.orderDesc(Arrays.asList(new String [] {"age"}))
                        .last("limit 1,3")
        );
        System.out.println(emps);

    }


    /**
     * 通用 删除操作
     */
    @Test
    public void testCommonDelete() {
        //1 .根据id进行删除
        Integer result = employeeMapper.deleteById(13);
        System.out.println("result: " + result);
        //2. 根据 条件进行删除
//		Map<String,Object> columnMap = new HashMap<>();
//		columnMap.put("last_name", "MP");
//		columnMap.put("email", "mp@atguigu.com");
//		Integer result = employeeMapper.deleteByMap(columnMap);
//		System.out.println("result: " + result );

        //3. 批量删除
//		List<Integer> idList = new ArrayList<>();
//		idList.add(3);
//		idList.add(4);
//		idList.add(5);
//		Integer result = employeeMapper.deleteBatchIds(idList);
//		System.out.println("result: " + result );
    }

    /**
     * 通用 查询操作
     */
    @Test
    public void testCommonSelect() {
        //1. 通过id查询
//		Employee employee = employeeMapper.selectById(5);
//		System.out.println(employee);

        //3. 通过多个id进行查询    <foreach>
        List<Integer> idList = Arrays.asList(4, 5, 6, 7);
        List<Employee> emps = employeeMapper.selectBatchIds(idList);
        System.out.println(emps);

        //4. 通过Map封装条件查询
//		Map<String,Object> columnMap = new HashMap<>();
//		columnMap.put("last_name", "Tom");
//		columnMap.put("gender", 1);
//		
//		List<Employee> emps = employeeMapper.selectByMap(columnMap);
//		System.out.println(emps);

    }

    @Test
    public void testSelectByQueryWrapper() {

        //2. 通过多个列进行查询    id  +  lastName
        Employee employee = new Employee();
        //employee.setId(7);
        employee.setLastName("小泽老师");
        employee.setGender(0);

        Employee result = employeeMapper.selectOne(new QueryWrapper<>(employee).select("id", "last_name"));

//        Employee result = employeeMapper.selectOne(new QueryWrapper<Employee>().select("id", "last_name")
//                .eq(employee.getId() != null, "id", employee.getId())
//                .eq(employee.getLastName() != null, "last_name", employee.getLastName())
//                .eq(employee.getGender() != null, "gender", employee.getGender())
//        );

        System.out.println("result: " + result);
    }


    /**
     * 通用 更新操作
     */
    @Test
    public void testCommonUpdate() {
        //初始化修改对象
        Employee employee = new Employee();
        employee.setId(7L);
        employee.setLastName("小泽");
        employee.setEmail("xz1@sina.com");
        employee.setGender(0);
//        employee.setVersion(0); //测试乐观锁
        //employee.setAge(33); //非空的属性不出现到SQL语句中

        Integer result = employeeMapper.updateById(employee);
        System.out.println("result: " + result);
    }

    @Test
    public void testLogicDelete() {

        int affectNum = employeeMapper.deleteById(2);
        System.out.println(affectNum);

//        Employee employee = employeeMapper.selectById(2);
//        System.out.println(employee);
    }


    /**
     * 通用 插入操作
     */
    @Test
    public void testCommonInsert() {

        //初始化Employee对象
        Employee employee = new Employee();
        employee.setLastName("MP");
        employee.setEmail("mp@atguigu.com");
        employee.setGender(1);
        employee.setAge(22);
        employee.setSalary(20000.0);
        //插入到数据库
        //insert方法在插入时， 会根据实体类的每个属性进行非空判断，只有非空的属性对应的字段才会出现到SQL语句中
        Integer result = employeeMapper.insert(employee);
        System.out.println("result: " + result);

        //获取当前数据在数据库中的主键值
        Long key = employee.getId();
        System.out.println("key:" + key);
    }


    @Test
    public void testDataSource() throws Exception {
        DataSource ds = ioc.getBean("dataSource", DataSource.class);
        System.out.println(ds);
        Connection conn = ds.getConnection();
        System.out.println(conn);
    }


}
