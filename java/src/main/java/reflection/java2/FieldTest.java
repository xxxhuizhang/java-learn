package reflection.java2;


import org.junit.Test;
import reflection.java1.MyAnnotation;
import reflection.java1.Person;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 获取当前运行时类的属性结构
 *
 * @author shkstart
 * @create 2019 下午 3:23
 */
public class FieldTest {

    @Test
    public void test1() {

        Class clazz = Person.class;

        //获取属性结构
        //getFields():获取当前运行时类及其父类中声明为public访问权限的属性
        Field[] fields = clazz.getFields();
        for (Field f : fields) {
            System.out.println(f);
        }
        System.out.println();

        //getDeclaredFields():获取当前运行时类中声明的所有属性。（不包含父类中声明的属性）
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            System.out.println(f);
        }
    }

    //权限修饰符  数据类型 变量名
    @Test
    public void test2() {
        Class clazz = Person.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            //1.权限修饰符
            int modifier = f.getModifiers();
            System.out.print(Modifier.toString(modifier) + "\t");

            //2.数据类型
            Class type = f.getType();

            if (type == Date.class) {
                System.out.print("类型为:" + Date.class.toString());
            }

            System.out.print(type.getName() + "\t");

            //3.变量名
            String fName = f.getName();
            System.out.print(fName);

            Annotation[] declaredAnnotations = f.getDeclaredAnnotations();

            for (Annotation a : declaredAnnotations) {
                if (a.annotationType() == MyAnnotation.class) {
                    System.out.println("相同:" + a);
                    System.out.println("注解的值为:" + ((MyAnnotation) a).value());
                }
                System.out.println(a);
                System.out.println(a.getClass() + "---" + MyAnnotation.class);
                System.out.println(a.annotationType());
            }

            System.out.println();
        }
    }


    @Test
    public void datetest() {

//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String localTime = df.format(LocalDateTime.now());
        System.out.println("LocalDateTime转成String类型的时间：" + localTime);

//        LocalDateTime ldt = LocalDateTime.parse("2018-06-01 10:12:05", df);
//        LocalDateTime ldt = LocalDateTime.parse("2018-06-01", df);
        LocalDate ldt = LocalDate.parse("2018-06-01", df);
        System.out.println("String类型的时间转成LocalDateTime：" + ldt);


    }

    @Test
    public void testPatternConventer() {
        String fromDateStr = "2018-06-01";
        String fromPattern = "yyyy-MM-dd";
        String toPattern = "yyyy/MM/dd";
        String toDateStr = patternConventer(fromDateStr, fromPattern, toPattern, false);
        System.out.println("----toDateStr: " + toDateStr);
    }

    public String patternConventer(String fromDateStr, String fromPattern, String toPattern, Boolean isDateTime) {
        DateTimeFormatter fromFormatter = DateTimeFormatter.ofPattern(fromPattern);
        DateTimeFormatter toFormatter = DateTimeFormatter.ofPattern(toPattern);
        String toDateStr = "";
        if (isDateTime) {
            LocalDateTime fromLocalDate = LocalDateTime.parse(fromDateStr, fromFormatter);
            toDateStr = toFormatter.format(fromLocalDate);
        } else {
            LocalDate fromLocalDate = LocalDate.parse(fromDateStr, fromFormatter);
            toDateStr = toFormatter.format(fromLocalDate);
        }
        return toDateStr;
    }

    public Date dateStrToDate(String fromDateStr, String fromPattern, Boolean isDateTime) {
        DateTimeFormatter fromFormatter = DateTimeFormatter.ofPattern(fromPattern);
        Instant instant = null;
        if (isDateTime) {
            LocalDateTime dateTime = LocalDateTime.parse(fromDateStr, fromFormatter);
            instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        } else {
            LocalDate date = LocalDate.parse(fromDateStr, fromFormatter);
            instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        }
        return Date.from(instant);
    }


    public Date stringToDate(String time, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);//"yyyy-MM-dd"
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
