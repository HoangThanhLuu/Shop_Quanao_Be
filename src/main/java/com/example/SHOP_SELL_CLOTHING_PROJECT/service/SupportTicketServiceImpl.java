package com.example.SHOP_SELL_CLOTHING_PROJECT.service;

/**
 * Project: SHOP_SELL_CLOTHING_PROJECT
 * Date: 2025/03/15
 * Time: 11:24 PM
 */

import com.example.SHOP_SELL_CLOTHING_PROJECT.IService.SupportTicketService;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.SupportTicket;
import com.example.SHOP_SELL_CLOTHING_PROJECT.model.TicketResponse;
import com.example.SHOP_SELL_CLOTHING_PROJECT.repository.SupportTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ 2025. All rights reserved
 */

@Service
@Transactional
public class SupportTicketServiceImpl implements SupportTicketService {
    @Autowired
    private SupportTicketRepository supportTicketRepository;

    @Override
    public Integer createTicket(SupportTicket ticket) {
        return supportTicketRepository.createTicket(
                ticket.getUser().getUserId(),
                ticket.getOrder() != null ? ticket.getOrder().getOrderId() : null,
                ticket.getSubject(),
                ticket.getMessage()
        );
    }

    @Override
    public Integer createResponse(TicketResponse response) {
        return supportTicketRepository.createResponse(
                response.getTicket().getTicketId(),
                response.getUser().getUserId(),
                response.getMessage()
        );
    }

    @Override
    public List<SupportTicket> getUserTickets(Integer userId) {
        return supportTicketRepository.getUserTickets(userId);
    }

    @Override
    public List<TicketResponse> getTicketResponses(Integer ticketId) {
        return supportTicketRepository.getTicketResponses(ticketId);
    }
}
