package com.example.SHOP_SELL_CLOTHING_PROJECT.service;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:10 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.IService.ProductService;
import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.ProductDTO;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Product;
import com.example.SHOP_SELL_CLOTHING_PROJECT.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Integer createProduct(Product product, String sizes) {
        return productRepository.createProduct(
                product.getProductsName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getCategoryId(),
                sizes
        );
    }

    @Override
    public ProductDTO getProductById(Integer productId) {
        return productRepository.getProductById(productId);
    }

    @Override
    public List<Product> searchProducts(String searchTerm, Integer categoryId,
                                        BigDecimal minPrice, BigDecimal maxPrice,
                                        Integer page, Integer pageSize) {
        return productRepository.searchProducts(
                searchTerm, categoryId, minPrice, maxPrice, page, pageSize
        );
    }

    @Override
    public List<Product> getAvailableProducts(Integer page, Integer pageSize) {
        return productRepository.getAvailableProducts(page, pageSize);
    }

    @Override
    public boolean updateProductStatus(Integer productId, String status) {
        return productRepository.updateProductStatus(productId, status);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.updateProduct(
                product.getProductId(),
                product.getProductsName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getCategoryId()
        );
    }
}
