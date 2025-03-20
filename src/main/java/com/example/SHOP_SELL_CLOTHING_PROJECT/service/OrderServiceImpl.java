package com.example.SHOP_SELL_CLOTHING_PROJECT.service;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:23 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.IService.OrderService;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Order;
import com.example.SHOP_SELL_CLOTHING_PROJECT.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ 2025. All rights reserved
 */

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Integer createOrder(Order order) {
        return orderRepository.createOrder(
                order.getUser().getUserId(),
                order.getPaymentMethod().toString(),
                order.getShippingAddress(),
                order.getBillingAddress()
        );
    }

    @Override
    public void processPayment(Integer orderId, String paymentMethod, String transactionCode) {
        orderRepository.processPayment(orderId, paymentMethod, transactionCode);
    }
}
