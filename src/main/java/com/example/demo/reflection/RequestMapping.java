package com.example.demo.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE, ElementType.METHOD})//클래스에도 붙이고 메소드에도 붙일 수 있다
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	String value() default "";
	
	RequestMethod[] method() default {};
}
