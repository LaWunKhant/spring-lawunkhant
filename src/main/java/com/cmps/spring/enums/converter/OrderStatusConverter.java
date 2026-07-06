package com.cmps.spring.enums.converter;

import java.util.Arrays;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.cmps.spring.enums.OrderStatus;

/**
 * OrderStatusの数値とEnum間の自動変換を行うコンバーター
 * @Converter(autoApply = true) により、全EntityのOrderStatusフィールドに自動適用される
 */
@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {

    /**
     * JavaオブジェクトからDB保存値（Integer）へ変換する
     * 実行タイミング：リポジトリのsave()などでDBへデータを書き込む直前
     */
    @Override
    public Integer convertToDatabaseColumn(OrderStatus attribute) {
        // Enumがnullの場合はDBにもnullを保存する
        // Enumが保持している数値（code）を返す
        return (attribute == null) ? null : attribute.getCode();
    }

    /**
     * DBの数値（Integer）からJavaオブジェクト（Enum）へ逆変換する
     * 実行タイミング：findById()などでDBからデータを読み込んだ直後
     */
    @Override
    public OrderStatus convertToEntityAttribute(Integer dbData) {
        // DBの値がnullの場合はJava側もnullにする
        if (dbData == null) return null;

        // コード値から一致するEnumを探して返すロジック
        return Arrays.stream(OrderStatus.values())
                .filter(s -> s.getCode() == dbData).findFirst()
                // もしDBに定義外の数値（例：99など）が入っていた場合は例外を投げる
                .orElseThrow(IllegalArgumentException::new);
    }
}