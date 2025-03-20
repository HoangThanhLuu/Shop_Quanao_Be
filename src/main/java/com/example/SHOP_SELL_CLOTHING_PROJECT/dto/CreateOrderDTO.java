package com.example.SHOP_SELL_CLOTHING_PROJECT.dto;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/16
 * Time: 12:18 PM
 */

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Data
public class CreateOrderDTO {
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotBlank(message = "Billing address is required")
    private String billingAddress;

    @Valid
    private List<OrderItemDTO> items;
}