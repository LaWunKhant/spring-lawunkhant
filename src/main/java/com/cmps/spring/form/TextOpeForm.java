package com.cmps.spring.form;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

import com.cmps.spring.validation.annotation.FileExtension;
import com.cmps.spring.validation.annotation.FileNotEmpty;
import com.cmps.spring.validation.annotation.FileSize;

import lombok.Data;

@Data
public class TextOpeForm implements Serializable {

	// テキストファイル
	@FileSize
	@FileNotEmpty
	@FileExtension
	private MultipartFile file;

	// 選択したファイル名（追記用）
	private String selectedFileName;

	// 追記するテキスト
	private String textPlus;
}