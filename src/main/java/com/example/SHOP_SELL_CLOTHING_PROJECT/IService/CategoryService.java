package com.example.SHOP_SELL_CLOTHING_PROJECT.IService;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:46 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.model.APIResponse;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Category;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

public interface CategoryService {
    APIResponse<String> createCategory(Category category) throws JsonProcessingException;
    void updateCategory(Category category);
    void deleteCategory(Integer categoryId);
    List<Category> getAllCategories();
    Category getCategoryById(Integer categoryId);
    List<Category> getRootCategories();
    List<Category> getSubCategories(Integer parentId);
}
