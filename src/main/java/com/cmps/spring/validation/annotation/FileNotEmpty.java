package com.cmps.spring.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cmps.spring.validation.FileNotEmptyValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = FileNotEmptyValidator.class)////このアノテーションを使用したときに実行されるクラスを指定
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileNotEmpty {
	String message() default "ファイルを添付してください。";////エラーメッセージ

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}