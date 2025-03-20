package com.example.SHOP_SELL_CLOTHING_PROJECT.repository;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:22 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

/**
 * @ 2025. All rights reserved
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Procedure(procedureName = "SP_ORDER_CREATE")
    Integer createOrder(Integer userId, String paymentMethod,
                        String shippingAddress, String billingAddress);

    @Procedure(procedureName = "SP_PAYMENT_PROCESS")
    void processPayment(Integer orderId, String paymentMethod, String transactionCode);
}
