package com.cmps.spring.validation;

import java.util.regex.Pattern;
import org.springframework.web.multipart.MultipartFile;
import com.cmps.spring.validation.annotation.FileExtension;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileExtensionValidator implements ConstraintValidator<FileExtension, MultipartFile> {

	private String regExp;

	@Override
	public void initialize(FileExtension constraintAnnotation) {
		this.regExp = constraintAnnotation.regExp();
	}

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		// ファイル未添付の場合はバリデーションしない
		if (file == null || file.isEmpty()) return true;

		//正規表現パターンを作成（regExpがtxtなら、「○○.txt」）
		//任意の文字(.*) + ドット(\\.) + 指定拡張子(regExp) + 終端($)
		//Pattern.CASE_INSENSITIVEを第2引数に与えると大文字小文字を区別しない
		String regex = ".*\\.(" + regExp + ")$";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		//正規表現チェック
		return pattern.matcher(file.getOriginalFilename()).find();
	}
}