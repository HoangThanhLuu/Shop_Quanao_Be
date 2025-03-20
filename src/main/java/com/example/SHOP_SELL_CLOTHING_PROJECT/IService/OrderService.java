package com.example.SHOP_SELL_CLOTHING_PROJECT.IService;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:25 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Order;

/**
 * @ 2025. All rights reserved
 */

public interface OrderService {
    Integer createOrder(Order order);
    void processPayment(Integer orderId, String paymentMethod, String transactionCode);
}
