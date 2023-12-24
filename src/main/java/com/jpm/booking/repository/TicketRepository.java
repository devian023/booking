package com.jpm.booking.repository;

import com.jpm.booking.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByBuyerPhoneNumber(String buyerPhoneNumber);
}
