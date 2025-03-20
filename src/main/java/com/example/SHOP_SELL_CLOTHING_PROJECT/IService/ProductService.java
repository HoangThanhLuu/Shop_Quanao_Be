package com.example.SHOP_SELL_CLOTHING_PROJECT.IService;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:09 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.ProductDTO;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ 2025. All rights reserved
 */

public interface ProductService {
    Integer createProduct(Product product, String sizes);
    ProductDTO getProductById(Integer id);
    List<Product> searchProducts(String searchTerm, Integer categoryId,
                                 BigDecimal minPrice, BigDecimal maxPrice,
                                 Integer page, Integer pageSize);
    List<Product> getAvailableProducts(Integer page, Integer pageSize);
    boolean updateProductStatus(Integer productId, String status);
    void updateProduct(Product product);
}
