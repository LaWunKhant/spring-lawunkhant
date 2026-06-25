package com.cmps.spring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({ "code", "name", "age", "address" }) //@JsonPropertyOrderを付けることで、csvファイルに出力される順番が確実に保証される
@NoArgsConstructor //引数なしコンストラクタ、CsvMapperによるMappingのために必要
@Data
public class CsvDto {
	// ID  CSV処理にIDも含めたい場合は同様に追加可能
	//@JsonProperty("code")
	//private Long id;

	// コード
	@JsonProperty("code") //@JsonPropertyで出力したいCSVファイルのカラム名を指定する
	private String code;

	// 名前
	@JsonProperty("name")
	private String name;

	// 年齢
	@JsonProperty("age")
	private Integer age;

	// アドレス
	@JsonProperty("address")
	private String address;

	// 引数ありコンストラクタ
	public CsvDto(String code, String name, Integer age, String address) {
		this.code = code;
		this.name = name;
		this.age = age;
		this.address = address;
	}
}