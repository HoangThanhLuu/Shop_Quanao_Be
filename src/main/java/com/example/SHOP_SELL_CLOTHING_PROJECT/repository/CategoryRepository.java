package com.example.SHOP_SELL_CLOTHING_PROJECT.repository;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:44 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Category;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ 2025. All rights reserved
 */

@Repository
public class CategoryRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public Map<String, Object> createCategory(String categoriesName, String description, Integer parentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Map<String, Object> result = new HashMap<>();
        
        try {
            transaction.begin();
            
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("SP_CATEGORY_CREATE")
                    .registerStoredProcedureParameter("p_CATEGORIES_NAME", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_DESCRIPTION", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_PARENT_ID", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_CODE", Integer.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter("p_CATEGORY_ID", Integer.class, ParameterMode.OUT)
                    .setParameter("p_CATEGORIES_NAME", categoriesName)
                    .setParameter("p_DESCRIPTION", description)
                    .setParameter("p_PARENT_ID", parentId);
    
            query.execute();
            
            Integer code = (Integer) query.getOutputParameterValue("p_CODE");
            Integer categoryId = (Integer) query.getOutputParameterValue("p_CATEGORY_ID");
            
            result.put("CODE", code);
            result.put("CATEGORY_ID", categoryId);
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            result.put("CODE", 1); // Generic error code
            result.put("ERROR", e.getMessage());
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        
        return result;
    }

    public void updateCategory(Integer categoryId, String categoriesName, String description, Integer parentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("SP_CATEGORY_UPDATE")
                    .registerStoredProcedureParameter("p_CATEGORY_ID", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_CATEGORIES_NAME", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_DESCRIPTION", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_PARENT_ID", Integer.class, ParameterMode.IN)
                    .setParameter("p_CATEGORY_ID", categoryId)
                    .setParameter("p_CATEGORIES_NAME", categoriesName)
                    .setParameter("p_DESCRIPTION", description)
                    .setParameter("p_PARENT_ID", parentId);

            query.execute();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    public void deleteCategory(Integer categoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("SP_CATEGORY_DELETE")
                    .registerStoredProcedureParameter("p_CATEGORY_ID", Integer.class, ParameterMode.IN)
                    .setParameter("p_CATEGORY_ID", categoryId);

            query.execute();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    public List<Category> getAllCategories() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("SP_CATEGORY_GET_ALL", Category.class);

            return query.getResultList();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    public Category getCategoryById(Integer categoryId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("SP_CATEGORY_GET_BY_ID", Category.class)
                    .registerStoredProcedureParameter("p_CATEGORY_ID", Integer.class, ParameterMode.IN)
                    .setParameter("p_CATEGORY_ID", categoryId);

            List<Category> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    public List<Category> getRootCategories() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("SP_CATEGORIES_ROOT_GET", Category.class);

            return query.getResultList();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    public List<Category> getSubCategories(Integer parentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("SP_CATEGORIES_CHILDREN_GET", Category.class)
                    .registerStoredProcedureParameter("p_PARENT_ID", Integer.class, ParameterMode.IN)
                    .setParameter("p_PARENT_ID", parentId);

            return query.getResultList();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }
}
