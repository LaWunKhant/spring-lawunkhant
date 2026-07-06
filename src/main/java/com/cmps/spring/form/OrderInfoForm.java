package com.cmps.spring.form;

import lombok.Data;

@Data
public class OrderInfoForm {
    private Integer totalPrice;
    private Integer statusCode;  // 数値で受け取る（後でEnumに変換）
}