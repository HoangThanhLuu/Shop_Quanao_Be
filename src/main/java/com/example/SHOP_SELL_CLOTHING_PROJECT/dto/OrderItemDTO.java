package com.example.SHOP_SELL_CLOTHING_PROJECT.dto;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/16
 * Time: 12:18 PM
 */

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemDTO {
    @NotNull(message = "Product ID is required")
    private Integer productId;

    @NotNull(message = "Variant ID is required")
    private Integer variantId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
