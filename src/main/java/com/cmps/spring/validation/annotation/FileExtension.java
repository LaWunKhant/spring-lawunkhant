package com.cmps.spring.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cmps.spring.validation.FileExtensionValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = FileExtensionValidator.class)////実行クラスを指定
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileExtension {
	String message() default "無効な拡張子です。";////エラーメッセージ

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String regExp() default "txt"; //デフォルトはtxt ////実装クラスで使用する変数のデフォルト値
}