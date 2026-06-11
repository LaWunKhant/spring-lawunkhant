package com.cmps.spring.form;

import java.io.Serializable;
import lombok.Data;

@Data
public class ManualForm implements Serializable {

	// 名前
	private String userName;
	
	// 出身
	private String comeFrom;
	
	// 年齢
	private Integer age;
}