package com.cbhlife.mybatis.test;

import com.cbhlife.mybatis.bean.Employee;
import com.cbhlife.mybatis.bean.OraclePage;
import com.cbhlife.mybatis.dao.EmployeeMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class PageHelperTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testPageHelper() throws IOException {
        // 1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            Page<Object> page = PageHelper.startPage(1, 3);
            List<Employee> emps = mapper.getAllEmployees();
            for (Employee employee : emps) {
                System.out.println(employee);
            }
            System.out.println("当前页码：" + page.getPageNum());
            System.out.println("总记录数：" + page.getTotal());
            System.out.println("每页的记录数：" + page.getPageSize());
            System.out.println("总页码：" + page.getPages());

            System.out.println("------------------------------");

            //传入要连续显示多少页
            PageInfo<Employee> info = new PageInfo<>(emps, 5);
            System.out.println("当前页码：" + info.getPageNum());
            System.out.println("总记录数：" + info.getTotal());
            System.out.println("每页的记录数：" + info.getPageSize());
            System.out.println("总页码：" + info.getPages());
            System.out.println("是否第一页：" + info.isIsFirstPage());
            System.out.println("列表 : " + info.getList() + "---||");
            System.out.println("连续显示的页码：");
            int[] nums = info.getNavigatepageNums();
            for (int i = 0; i < nums.length; i++) {
                System.out.println(nums[i]);
            }

        } finally {
            openSession.close();
        }
    }

    @Test
    public void testBatch() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //可以执行批量操作的sqlSession
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        long start = System.currentTimeMillis();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            for (int i = 0; i < 10000; i++) {
                mapper.addEmp(new Employee(UUID.randomUUID().toString().substring(0, 5), "email", "1"));
            }
            openSession.commit();
            long end = System.currentTimeMillis();
            //批量：（预编译sql一次==>设置参数===>10000次===>执行（1次））
            //Parameters: 616c1(String), b(String), 1(String)==>4598
            //非批量：（预编译sql=设置参数=执行）==》10000    10200
            System.out.println("执行时长：" + (end - start));
        } finally {
            openSession.close();
        }

    }


    /**
     * oracle分页：
     * 借助rownum：行号；子查询；
     * 存储过程包装分页逻辑
     *
     * @throws IOException
     */
    @Test
    public void testProcedure() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            OraclePage page = new OraclePage();
            page.setStart(1);
            page.setEnd(5);
            mapper.getPageByProcedure(page);

            System.out.println("总记录数：" + page.getCount());
            System.out.println("查出的数据：" + page.getEmps().size());
            System.out.println("查出的数据：" + page.getEmps());
        } finally {
            openSession.close();
        }
    }

}
