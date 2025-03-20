package com.example.SHOP_SELL_CLOTHING_PROJECT.IService;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:25 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.model.SupportTicket;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.TicketResponse;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

public interface SupportTicketService {
    Integer createTicket(SupportTicket ticket);
    Integer createResponse(TicketResponse response);
    List<SupportTicket> getUserTickets(Integer userId);
    List<TicketResponse> getTicketResponses(Integer ticketId);
}
