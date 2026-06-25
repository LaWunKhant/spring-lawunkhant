package com.cmps.spring.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "images")
public class Image implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// BLOBデータ - LONGBLOB指定
	@Column(columnDefinition = "LONGBLOB")
	private byte[] data;

	// ファイル名
	@Column
	private String name;

	// コンテントタイプ
	@Column
	private String contentType;

	// 引数ありコンストラクタ
	public Image(byte[] data, String name, String contentType) {
		this.data = data;
		this.name = name;
		this.contentType = contentType;
	}
}
