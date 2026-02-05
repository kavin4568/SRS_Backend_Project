package com.examly.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Ticket;
import com.examly.springapp.repository.TicketRepo;
import java.util.List;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepo ticketRepo;

    public Ticket addTicket(Ticket ticket) {
        return ticketRepo.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
    }

    public Ticket updateTicket(Long id, Ticket ticket) {
        Ticket existing = ticketRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        existing.setSubject(ticket.getSubject());
        existing.setDescription(ticket.getDescription());
        existing.setStatus(ticket.getStatus());
        return ticketRepo.save(existing);
    }

    public void deleteTicket(Long id) {
        if (!ticketRepo.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found with id: " + id);
        }
        ticketRepo.deleteById(id);
    }
}
