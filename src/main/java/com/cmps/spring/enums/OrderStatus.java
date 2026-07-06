package com.cmps.spring.enums;

import java.util.Arrays;

public enum OrderStatus {
    // ① 定数（インスタンス）の定義
    ORDERED(1, "注文済み"),
    PREPARING(2, "発送準備中"),
    SHIPPED(3, "発送完了");

    // ② フィールド（属性）
    private final int code;
    private final String name;

    // ③ コンストラクタ
    private OrderStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    // ④ メソッド
    public int getCode() { 
        return code; 
    }
    
    public String getName() { 
        return name; 
    }
    
    // 数値からEnumを探す「逆引き」メソッド 
    public static OrderStatus fromCode(int code) {
        return Arrays.stream(OrderStatus.values())
                .filter(s -> s.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}