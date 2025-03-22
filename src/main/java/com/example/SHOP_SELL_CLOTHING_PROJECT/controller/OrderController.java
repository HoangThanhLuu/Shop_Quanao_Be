package com.example.SHOP_SELL_CLOTHING_PROJECT.controller;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/16
 * Time: 11:50 AM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.ENUM.OrderStatus;
import com.example.SHOP_SELL_CLOTHING_PROJECT.ENUM.PaymentMethod;
import com.example.SHOP_SELL_CLOTHING_PROJECT.ENUM.PaymentStatus;
import com.example.SHOP_SELL_CLOTHING_PROJECT.IService.OrderService;
import com.example.SHOP_SELL_CLOTHING_PROJECT.IService.ProductService;
import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.*;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.APIResponse;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Order;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<APIResponse<Integer>> createOrder(
            @Valid @RequestBody CreateOrderDTO orderDTO) throws JsonProcessingException {
        Order order = convertToOrder(orderDTO);
        Integer orderId = orderService.createOrder(order);
        return ResponseEntity.ok(new APIResponse<>(12, "Order created successfully", orderId));
    }

    @PostMapping("/{orderId}/payment")
    public ResponseEntity<APIResponse<Void>> processPayment(
            @PathVariable Integer orderId,
            @Valid @RequestBody PaymentDTO paymentDTO) {
        orderService.processPayment(orderId, paymentDTO.getPaymentMethod(), paymentDTO.getTransactionCode());
        return ResponseEntity.ok(new APIResponse<>(13, "Payment processed successfully", null));
    }

    private Order convertToOrder(CreateOrderDTO orderDTO) throws JsonProcessingException {
        Order order = new Order();

        // Set user
        User user = new User();
        user.setUserId(Integer.parseInt("1"));
        order.setUser(user);

        // Set order details
        order.setPaymentMethod(PaymentMethod.valueOf(orderDTO.getPaymentMethod()));
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setBillingAddress(orderDTO.getBillingAddress());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.UNPAID);

        // Calculate total amount from items
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItemDTO item : orderDTO.getItems()) {
//            ProductDTO productDTO = productService.getProductById(item.getProductId());
//            totalAmount = totalAmount.add(productDTO.getPrice().multiply(
//                    new BigDecimal(item.getQuantity())
//            ));
        }
        order.setTotalAmount(totalAmount);

        return order;
    }
}
