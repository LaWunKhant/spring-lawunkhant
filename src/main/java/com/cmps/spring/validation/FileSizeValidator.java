package com.cmps.spring.validation;

import org.springframework.web.multipart.MultipartFile;

import com.cmps.spring.validation.annotation.FileSize;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

	private long max;

	@Override
	public void initialize(FileSize constraintAnnotation) {
		this.max = constraintAnnotation.max();////maxの値を取得
	}

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		return file.getSize() <= max;
	}
}