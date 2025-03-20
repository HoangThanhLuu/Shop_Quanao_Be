package com.example.SHOP_SELL_CLOTHING_PROJECT.service;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:46 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.ENUM.ResponseType;
import com.example.SHOP_SELL_CLOTHING_PROJECT.IService.CategoryService;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.APIResponse;
import com.example.SHOP_SELL_CLOTHING_PROJECT.repository.CategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ 2025. All rights reserved
 */

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public APIResponse<String> createCategory(Category category) throws JsonProcessingException {
        Map<String, Object> result = categoryRepository.createCategory(
                category.getCategoriesName(),
                category.getDescription(),
                category.getParent() != null ? category.getParent().getCategoryId() : null
        );

        int code = (Integer) result.get("CODE");
        int categoryId = result.get("CATEGORY_ID") == null ? -1 : (Integer) result.get("CATEGORY_ID");

        if (code == 0)
            return new APIResponse<>(code, "", objectMapper.writeValueAsString(categoryId), ResponseType.SUCCESS);
        else
            return new APIResponse<>(code, "", objectMapper.writeValueAsString(categoryId), ResponseType.ERROR);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.updateCategory(
                category.getCategoryId(),
                category.getCategoriesName(),
                category.getDescription(),
                category.getParent() != null ? category.getParent().getCategoryId() : null
        );
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteCategory(categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }

    @Override
    public List<Category> getRootCategories() {
        return categoryRepository.getRootCategories();
    }

    @Override
    public List<Category> getSubCategories(Integer parentId) {
        return categoryRepository.getSubCategories(parentId);
    }
}
