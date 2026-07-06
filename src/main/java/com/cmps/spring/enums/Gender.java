package com.cmps.spring.enums;

public enum Gender {
    // 定数の定義
    MALE("男性"), FEMALE("女性");

    // フィールド
    private final String text;

    // コンストラクタ
    private Gender(String text) {
        this.text = text;
    }

    // Getter
    public String getText() {
        return text;
    }
}