package com.example.SHOP_SELL_CLOTHING_PROJECT.controller;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/16
 * Time: 11:47 AM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.IService.CartService;
import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.AddToCartDTO;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.APIResponse;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.CartItem;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/cart")
@Validated
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<APIResponse<Void>> addToCart(
            @Valid @RequestBody AddToCartDTO cartDTO) {
        cartService.addItemToCart(
                Integer.parseInt("1"),
                cartDTO.getProductId(),
                cartDTO.getVariantId(),
                cartDTO.getQuantity()
        );
        return ResponseEntity.ok(new APIResponse<>(2, "Item added to cart successfully", null));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<APIResponse<Void>> removeFromCart(@PathVariable Integer itemId) {
        cartService.removeItemFromCart(itemId);
        return ResponseEntity.ok(new APIResponse<>(3, "Item removed from cart successfully", null));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<CartItem>>> getCartItems() {
        List<CartItem> items = cartService.getCartItems(Integer.parseInt("1"));
        return ResponseEntity.ok(new APIResponse<>(4, "Cart items retrieved successfully", items));
    }
}
