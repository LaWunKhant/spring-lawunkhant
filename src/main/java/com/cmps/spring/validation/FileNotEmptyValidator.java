package com.cmps.spring.validation;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cmps.spring.validation.annotation.FileNotEmpty;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileNotEmptyValidator implements ConstraintValidator<FileNotEmpty, MultipartFile> {

	@Override
	public void initialize(FileNotEmpty constraintAnnotation) {////
	}

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		// ファイルが空ではないかどうか または ファイル名が長さを持つか（存在するか）判定
		return !file.isEmpty() || StringUtils.hasLength(file.getOriginalFilename());
	}
}