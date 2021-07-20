package com.cbhlife.mybatis.test;

import com.cbhlife.mybatis.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class EmployeeMapperPlusTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);

//			Employee empById = mapper.getEmpById(1);
//			System.out.println(empById);

            //表连接查询
//			Employee empAndDept = mapper.getEmpAndDept(1);
//			System.out.println(empAndDept);
//			System.out.println(empAndDept.getDept());

            //分步查询
//            Employee employee = mapper.getEmpByIdStep(3);
//            System.out.println(employee);
//            System.out.println(employee.getDept());

        } finally {
            openSession.close();
        }
    }


}
