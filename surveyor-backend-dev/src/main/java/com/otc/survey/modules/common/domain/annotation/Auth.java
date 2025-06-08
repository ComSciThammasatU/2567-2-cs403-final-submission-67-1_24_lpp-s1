package com.otc.survey.modules.common.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth 
{
	boolean authen() default false;
	
	boolean authorize() default false;
	
	String[] grantedRoleIds() default {};
	
	String[] grantedUserIds() default {};
}