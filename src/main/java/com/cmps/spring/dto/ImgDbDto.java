package com.cmps.spring.dto;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgDbDto {
	// ファイル名
	private String name;
 
	// imgタグのsrc属性に記載する値
	private String srcText;
 
	// コンテントタイプ
	private String contentType;
}