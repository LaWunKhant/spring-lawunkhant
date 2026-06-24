package com.cmps.spring.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cmps.spring.validation.FileSizeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = FileSizeValidator.class)////このアノテーションを使用したときに実行されるクラスを指定
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileSize {
	String message() default "ファイルサイズが無効です。";////エラーメッセージ

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	long max() default 500 * 1024; //デフォルトが500kB ////実装クラスで使用する変数のデフォルト値を設定
}
