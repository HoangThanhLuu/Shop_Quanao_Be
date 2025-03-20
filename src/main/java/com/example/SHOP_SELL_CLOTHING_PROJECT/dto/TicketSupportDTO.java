package com.example.SHOP_SELL_CLOTHING_PROJECT.dto;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/16
 * Time: 1:23 PM
 */

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSupportDTO {
    // Ticket fields
    private Integer ticketId;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Message is required")
    private String message;

    private Integer orderId;

    // Response fields
    private Integer responseId;
    private Integer userId;
    private LocalDateTime createdAt;
    private List<TicketResponseDTO> responses;
}

