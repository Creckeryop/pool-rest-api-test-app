package ru.khabirov.poolApp.poolrestapitestapp.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.khabirov.poolApp.poolrestapitestapp.models.Ticket
import java.time.LocalDate

@Repository
interface TicketRepository : JpaRepository<Ticket, Int> {
    fun findAllByOrderDate(order_date: LocalDate):List<Ticket>
}