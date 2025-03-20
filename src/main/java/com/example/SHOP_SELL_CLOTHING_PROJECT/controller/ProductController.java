package com.example.SHOP_SELL_CLOTHING_PROJECT.controller;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/16
 * Time: 11:38 AM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.ENUM.ProductStatus;
import com.example.SHOP_SELL_CLOTHING_PROJECT.IService.ProductService;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.APIResponse;
import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.ProductDTO;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Product;
import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.ProductVariantDTO;
import jakarta.validation.Valid;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<APIResponse<Integer>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = convertToProduct(productDTO);
        String sizes = convertVariantsToSizeString(productDTO.getVariants());
        Integer productId = productService.createProduct(product, sizes);
        return ResponseEntity.ok(new APIResponse<>(14, "Product created successfully", productId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProductDTO>> getProduct(@PathVariable Integer id) {
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(new APIResponse<>(15, "Product retrieved successfully", productDTO));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Product>>> searchProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Product> products = productService.searchProducts(search, categoryId, minPrice, maxPrice, page, pageSize);
        return ResponseEntity.ok(new APIResponse<>(16, "Products retrieved successfully", products));
    }

    @GetMapping("/available")
    public ResponseEntity<APIResponse<List<Product>>> getAvailableProducts(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Product> products = productService.getAvailableProducts(page, pageSize);
        return ResponseEntity.ok(new APIResponse<>(17, "Available products retrieved successfully", products));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody ProductDTO productDTO) {
        Product product = convertToProduct(productDTO);
        product.setProductId(id);
        productService.updateProduct(product);
        return ResponseEntity.ok(new APIResponse<>(18, "Product updated successfully", null));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<APIResponse<Void>> updateProductStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        productService.updateProductStatus(id, status);
        return ResponseEntity.ok(new APIResponse<>(19, "Product status updated successfully", null));
    }

    private Product convertToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductsName(productDTO.getProductsName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setProductStatus(ProductStatus.AVAILABLE);

        Category category = new Category();
        category.setCategoryId(productDTO.getCategoryID());
        product.setCategory(category);

        // Calculate total stock from variants
        Integer totalStock = productDTO.getVariants() != null ?
                productDTO.getVariants().stream()
                        .mapToInt(ProductVariantDTO::getStockQuantity)
                        .sum() : 0;
        product.setStockQuantity(totalStock);

        return product;
    }

    private String convertVariantsToSizeString(List<ProductVariantDTO> variants) {
        if (variants == null || variants.isEmpty()) {
            return "";
        }
        return variants.stream()
                .map(ProductVariantDTO::getSize)
                .distinct()
                .sorted()
                .collect(Collectors.joining(","));
    }
}
