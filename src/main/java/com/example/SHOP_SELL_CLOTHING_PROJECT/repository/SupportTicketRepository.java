package com.example.SHOP_SELL_CLOTHING_PROJECT.repository;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:33 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.model.SupportTicket;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.TicketResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Integer> {
    @Procedure(procedureName = "SP_TICKET_SUPPORT_CREATE")
    Integer createTicket(Integer userId, Integer orderId, String subject, String message);

    @Procedure(procedureName = "SP_TICKET_RESPONSE_CREATE")
    Integer createResponse(Integer ticketId, Integer userId, String message);

    @Procedure(procedureName = "SP_TICKETS_SUPPORT_GET")
    List<SupportTicket> getUserTickets(Integer userId);

    @Procedure(procedureName = "SP_TICKET_RESPONSES_GET")
    List<TicketResponse> getTicketResponses(Integer ticketId);
}
