package com.example.SHOP_SELL_CLOTHING_PROJECT.repository;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:05 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.ProductDTO;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Procedure(procedureName = "SP_PRODUCT_CREATE")
    Integer createProduct(String productsName, String description,
                          BigDecimal price, Integer categoryId, String sizes);

    @Procedure(procedureName = "SP_PRODUCT_GET")
    ProductDTO getProductById(Integer productId);

    @Procedure(procedureName = "SP_PRODUCT_SEARCH")
    List<Product> searchProducts(String searchTerm, Integer categoryId,
                                 BigDecimal minPrice, BigDecimal maxPrice,
                                 Integer page, Integer pageSize);

    @Procedure(name = "SP_PRODUCTS_AVAILABLE_GET")
    List<Product> getAvailableProducts(Integer page, Integer pageSize);

    @Procedure(name = "SP_PRODUCT_STATUS_UPDATE")
    boolean updateProductStatus(Integer productId, String status);

    @Procedure(name = "SP_PRODUCT_UPDATE")
    void updateProduct(Integer productId, String productsName,
                       String description, BigDecimal price, Integer categoryId);
}
