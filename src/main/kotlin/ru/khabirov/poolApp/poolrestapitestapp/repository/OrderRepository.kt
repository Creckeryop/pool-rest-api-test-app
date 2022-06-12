package ru.khabirov.poolApp.poolrestapitestapp.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.khabirov.poolApp.poolrestapitestapp.models.Order
import java.time.LocalDate

@Repository
interface OrderRepository : JpaRepository<Order, Int> {
    fun findAllByClientId(client_id: Int): List<Order>
    fun findAllByDate(date: LocalDate): List<Order>
    fun findAllByClientName(client_name: String): List<Order>
    fun findAllByClientNameAndDate(client_name: String, date: LocalDate): List<Order>
}