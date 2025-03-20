package com.example.SHOP_SELL_CLOTHING_PROJECT.dto;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/16
 * Time: 1:24 PM
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    private Integer responseId;
    private String message;
    private String userName;
    private Boolean isAdmin;
    private LocalDateTime createdAt;
}
