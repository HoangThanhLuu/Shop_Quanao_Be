package com.example.SHOP_SELL_CLOTHING_PROJECT.IService;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:25 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.model.CartItem;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

public interface CartService {
    void addItemToCart(Integer userId, Integer productId, Integer variantId, Integer quantity);
    void removeItemFromCart(Integer cartItemId);
    List<CartItem> getCartItems(Integer userId);
}
