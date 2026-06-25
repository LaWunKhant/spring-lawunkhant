package com.cmps.spring.form;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

import com.cmps.spring.validation.annotation.FileExtension;
import com.cmps.spring.validation.annotation.FileNotEmpty;
import com.cmps.spring.validation.annotation.FileSize;

import lombok.Data;

@Data
public class ImgForm implements Serializable {

	// 画像ファイル
	@FileNotEmpty(message = "画像ファイルを選択してください。")
	@FileSize(max = 200 * 1024) // 200kB以下
	@FileExtension(regExp = "jpg|jpeg|png|gif|bmp") // 画像拡張子のみ
	private MultipartFile file;
}