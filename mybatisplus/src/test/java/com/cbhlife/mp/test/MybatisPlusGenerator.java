package com.cbhlife.mp.test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

import static com.baomidou.mybatisplus.annotation.DbType.MYSQL;
import static com.baomidou.mybatisplus.annotation.DbType.POSTGRE_SQL;

public class MybatisPlusGenerator {

    public static void main(String[] args) {

        String database = "cbhlife";
        String user = "root";
        String passwrod = "root";
        String tablePrefix = "chat_";


        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        ///Users/imessage/Desktop/java/java-learn
        ///Users/imessage/Desktop/java/java-learn/mybatisplus
        projectPath += ("/" + scanner("请输入模块名"));

        globalConfig.setActiveRecord(true)
                .setOutputDir(projectPath + "/src/main/java")
                .setAuthor("xxxhuizhang")
                .setFileOverride(true)
                .setIdType(IdType.AUTO)
                .setServiceName("%sService")  //默认IEmployeeService 设置生成的service接口的名字的首字母是否为I
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setSwagger2(true)
                .setOpen(false);

        autoGenerator.setGlobalConfig(globalConfig);

        /**
         * 数据源配置
         * url:
         * mysql localhost:5432/cbhlife
         * postgresql  localhost:3306/mp
         */
        DataSourceConfig dsConfig = setDataSourceConfig(POSTGRE_SQL,
                "localhost:5432/" + database, user, passwrod);
        autoGenerator.setDataSource(dsConfig);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("请输入子模块名"));
        pc.setParent("mybatis.plus.generator");
        autoGenerator.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setTablePrefix(tablePrefix) //pc.getModuleName() + "_"
                .setInclude(scanner("表名，多个英文逗号分割").split(","));

        autoGenerator.setStrategy(strategy);
        autoGenerator.execute();
    }

    private static DataSourceConfig setDataSourceConfig(DbType dbType,
                                                        String url,
                                                        String user,
                                                        String password) {

        DataSourceConfig dsConfig = new DataSourceConfig();

        switch (dbType) {
            case MYSQL:
                dsConfig.setDbType(MYSQL)
                        .setDriverName("com.mysql.jdbc.Driver")
                        .setUrl("jdbc:mysql://" + url)
                        .setUsername(user)
                        .setPassword(password);
                break;

            case POSTGRE_SQL:
                dsConfig.setDbType(POSTGRE_SQL)
                        .setDriverName("org.postgresql.Driver")
                        .setUrl("jdbc:postgresql://" + url)
                        .setUsername(user)
                        .setPassword(password);
                break;

            default:
                break;
        }

        return dsConfig;
    }

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}