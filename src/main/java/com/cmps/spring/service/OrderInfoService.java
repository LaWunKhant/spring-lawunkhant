package com.cmps.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmps.spring.entity.OrderInfo;
import com.cmps.spring.enums.OrderStatus;
import com.cmps.spring.repository.OrderInfoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderInfoService {
    private final OrderInfoRepository orderInfoRepository;

    /**
     * 全注文を取得
     */
    public List<OrderInfo> findAll() {
        return orderInfoRepository.findAll();
    }

    /**
     * IDで注文を取得
     */
    public OrderInfo findById(Long id) {
        return orderInfoRepository.findById(id).orElse(null);
    }

    /**
     * 注文を保存
     * 数値（code）からEnumに変換して保存
     */
    public void save(OrderInfo orderInfo) {
        orderInfoRepository.save(orderInfo);
    }

    /**
     * 注文を削除
     */
    public void deleteById(Long id) {
        orderInfoRepository.deleteById(id);
    }
}