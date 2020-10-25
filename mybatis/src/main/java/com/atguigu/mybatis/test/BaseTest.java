package com.atguigu.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;

public class BaseTest {

//    public SqlSessionTemplate template = null;
//    ClassPathXmlApplicationContext context = null;
//
//    @Before
//    public void before() {
//        context = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
//        template = context.getBean(SqlSessionTemplate.class);
//    }

    SqlSessionFactory sqlSessionFactory = null;

    @Before
    public void getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }


}