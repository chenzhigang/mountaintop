package com.czg.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，可以允许重复请求
 */
@Inherited // 所标记的类的子类也会拥有这个注解
@Documented // 可以被Javadoc 或类似的工具文档化
@Retention(RetentionPolicy.RUNTIME) // 由JVM 加载，包含在类文件中，在运行时可以被获取到
@Target({ElementType.METHOD, ElementType.TYPE}) // 应用于类、接口（包括注解类型）、枚举、方法
public @interface AllowRepeat {

    String value() default "";

}
