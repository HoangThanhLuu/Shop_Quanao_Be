package com.example.SHOP_SELL_CLOTHING_PROJECT.service;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:23 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.IService.CartService;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.CartItem;
import com.example.SHOP_SELL_CLOTHING_PROJECT.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public void addItemToCart(Integer userId, Integer productId, Integer variantId, Integer quantity) {
        cartRepository.addCartItem(userId, productId, variantId, quantity);
    }

    @Override
    public void removeItemFromCart(Integer cartItemId) {
        cartRepository.removeCartItem(cartItemId);
    }

    @Override
    public List<CartItem> getCartItems(Integer userId) {
        return cartRepository.getCartItems(userId);
    }
}
