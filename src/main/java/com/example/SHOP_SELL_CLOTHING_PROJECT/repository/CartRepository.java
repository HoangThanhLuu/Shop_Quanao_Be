package com.example.SHOP_SELL_CLOTHING_PROJECT.repository;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:22 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.ENUM.ProductStatus;
import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.CartDTO;
import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.ImageDTO;
import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.ProductDTO;
import com.example.SHOP_SELL_CLOTHING_PROJECT.dto.ProductVariantDTO;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.Cart;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.CartItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ 2025. All rights reserved
 */

@Repository
@Slf4j
public class CartRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Map<String, Object> addCartItem(Integer userId, Integer productId, Integer variantId, Integer quantity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Map<String, Object> result = new HashMap<>();

        try {
            transaction.begin();

            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("SP_CART_ADD_ITEM")
                    .registerStoredProcedureParameter("p_USER_ID", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_PRODUCT_ID", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_VARIANT_ID", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_QUANTITY", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_CODE", Integer.class, ParameterMode.OUT)
                    .setParameter("p_USER_ID", userId)
                    .setParameter("p_PRODUCT_ID", productId)
                    .setParameter("p_VARIANT_ID", variantId)
                    .setParameter("p_QUANTITY", quantity);

            query.execute();
            Integer code = (Integer) query.getOutputParameterValue("p_CODE");
            result.put("CODE", code);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            result.put("CODE", 1);
            result.put("ERROR", e.getMessage());
            log.error("[CART REPOSITORY] Add Cart Item. ERROR: ", e);
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }

        return result;
    }

    public Map<String, Object> removeCartItem(Integer cartItemId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Map<String, Object> result = new HashMap<>();

        try {
            transaction.begin();

            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("SP_CART_REMOVE_ITEM")
                    .registerStoredProcedureParameter("p_CART_ITEM_ID", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_CODE", Integer.class, ParameterMode.OUT)
                    .setParameter("p_CART_ITEM_ID", cartItemId);

            query.execute();

            Integer code = (Integer) query.getOutputParameterValue("p_CODE");
            result.put("CODE", code);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            result.put("CODE", 1);
            result.put("ERROR", e.getMessage());
            log.error("[CART REPOSITORY] Remove Cart Item. ERROR: ", e);
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }

        return result;
    }

    public Map<String, Object> getCartItems(Integer userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Map<String, Object> result = new HashMap<>();

        try {
            transaction.begin();

            StoredProcedureQuery query = entityManager
                    .createStoredProcedureQuery("SP_CART_ITEMS_GET")
                    .registerStoredProcedureParameter("p_USER_ID", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_CODE", Integer.class, ParameterMode.OUT)
                    .setParameter("p_USER_ID", userId);

            query.execute();

            List<?> results = query.getResultList();
            Integer code = (Integer) query.getOutputParameterValue("p_CODE");

            List<CartDTO> cartDTOS = null;
            if (code == 0 && results != null && !results.isEmpty()) {
                cartDTOS = new ArrayList<>();
                for (Object item : results) {
                    Object[] row = (Object[]) item;
                    cartDTOS.add(mapCartFromRow(row));
                }
            }

            result.put("CODE", code);
            result.put("CART_ITEMS", cartDTOS);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            result.put("CODE", 1);
            result.put("ERROR", e.getMessage());
            log.error("[CART REPOSITORY] Get Cart Items. ERROR: ", e);
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }

        return result;
    }

    private CartDTO mapCartFromRow(Object[] row) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CartDTO cartDTO = new CartDTO();

        cartDTO.setCartItemID((Integer) row[0]);
        cartDTO.setCartID((Integer) row[1]);
        cartDTO.setProductId((Integer) row[2]);
        cartDTO.setVariantId((Integer) row[3]);
        cartDTO.setQuantity((Integer) row[4]);
        cartDTO.setProductName((String) row[5]);
        cartDTO.setPrice((BigDecimal) row[6]);
        cartDTO.setSize((String) row[7]);
        cartDTO.setImageURL((String) row[8]);

        return cartDTO;
    }
}
