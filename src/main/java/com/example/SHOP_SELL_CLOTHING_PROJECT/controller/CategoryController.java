package com.example.SHOP_SELL_CLOTHING_PROJECT.controller;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/16
 * Time: 12:54 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.IService.CategoryService;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.APIResponse;
import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.CategoryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ 2025. All rights reserved
 */

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<APIResponse<String>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws JsonProcessingException {
        Category category = convertToCategory(categoryDTO);
        APIResponse<String> resultData = categoryService.createCategory(category);
        return ResponseEntity.ok(new APIResponse<>(resultData.getCode(), resultData.getMessage(), resultData.getData(), resultData.getResponseType()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> updateCategory(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = convertToCategory(categoryDTO);
        categoryService.updateCategory(category);
        return ResponseEntity.ok(new APIResponse<>(6, "Category updated successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new APIResponse<>(7, "Category deleted successfully", null));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new APIResponse<>(8, "Categories retrieved successfully", categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<Category>> getCategoryById(@PathVariable Integer id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new APIResponse<>(9, "Category retrieved successfully", category));
    }

    @GetMapping("/root")
    public ResponseEntity<APIResponse<List<Category>>> getRootCategories() {
        List<Category> rootCategories = categoryService.getRootCategories();
        return ResponseEntity.ok(new APIResponse<>(10, "Root categories retrieved successfully", rootCategories));
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<APIResponse<List<Category>>> getChildCategories(@PathVariable Integer id) {
        List<Category> childCategories = categoryService.getSubCategories(id);
        return ResponseEntity.ok(new APIResponse<>(11, "Child categories retrieved successfully", childCategories));
    }

    private Category convertToCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setCategoryId(dto.getCategoryId());
        category.setCategoriesName(dto.getCategoriesName());
        category.setDescription(dto.getDescription());
        if (dto.getParentId() != null) {
            Category parent = new Category();
            parent.setCategoryId(dto.getParentId());
            category.setParent(parent);
        }
        return category;
    }

    private CategoryDTO convertToCategoryDTO(Category category) {
        return new CategoryDTO(
                category.getCategoryId(),
                category.getCategoriesName(),
                category.getDescription(),
                category.getParent() != null ? category.getParent().getCategoryId() : null,
                category.getSubCategories() != null ?
                        category.getSubCategories().stream()
                                .map(this::convertToCategoryDTO)
                                .collect(Collectors.toList()) :
                        null
        );
    }
}
