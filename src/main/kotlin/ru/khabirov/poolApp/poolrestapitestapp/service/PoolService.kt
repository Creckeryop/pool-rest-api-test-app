package ru.khabirov.poolApp.poolrestapitestapp.service

import org.springframework.stereotype.Component
import ru.khabirov.poolApp.poolrestapitestapp.exception.IllegalArgumentFoundException
import ru.khabirov.poolApp.poolrestapitestapp.exception.NoSuchElementFoundException
import ru.khabirov.poolApp.poolrestapitestapp.models.*
import ru.khabirov.poolApp.poolrestapitestapp.repository.ClientRepository
import ru.khabirov.poolApp.poolrestapitestapp.repository.HolidayRepository
import ru.khabirov.poolApp.poolrestapitestapp.repository.OrderRepository
import ru.khabirov.poolApp.poolrestapitestapp.repository.TicketRepository
import java.time.LocalDate

@Component
class PoolService(
    private val clientRepository: ClientRepository,
    private val holidayRepository: HolidayRepository,
    private val orderRepository: OrderRepository,
    private val ticketRepository: TicketRepository
) {
    companion object {
        val CommonTimeRange: IntRange = (12 until 18)
        val HolidayTimeRange: IntRange = (12 until 17)
        const val MaxOrdersInHour: Int = 10
        const val MaxOrdersInDayForSameClient: Int = 1
    }

    fun addClient(client: Client) {
        client.id = -1
        clientRepository.save(client)
    }

    @Throws(NoSuchElementFoundException::class)
    fun getClient(clientId: Int): Client {
        val client = clientRepository.findById(clientId)
        if (client.isEmpty)
            throw NoSuchElementFoundException("Client with given clientId doesn't exist")
        return client.get()
    }

    fun getAllClients(): List<ClientInfo> {
        return clientRepository.findAll().map { ClientInfo(it.id, it.name) }
    }

    @Throws(NoSuchElementFoundException::class)
    fun updateClient(client: Client) {
        if (clientRepository.findById(client.id).isEmpty)
            throw NoSuchElementFoundException("Client with given clientId doesn't exist")
        clientRepository.save(client)

    }

    @Throws(IllegalArgumentFoundException::class)
    fun addHoliday(date: LocalDate) {
        if (holidayRepository.findByDate(date) != null)
            throw IllegalArgumentFoundException("Holiday with given date already exists")
        holidayRepository.save(Holiday(-1, date))
    }

    fun getHolidays(): List<Holiday> {
        return holidayRepository.findAll()
    }

    @Throws(NoSuchElementFoundException::class)
    fun removeHoliday(date: LocalDate) {
        if (holidayRepository.findByDate(date) == null)
            throw NoSuchElementFoundException("Holiday with given date not found")
        holidayRepository.deleteByDate(date)
    }

    fun getAllOrders(date: LocalDate): MutableList<ScheduleResponse> {
        val dateTickets = ticketRepository.findAllByOrderDate(date)
        val responseList: MutableList<ScheduleResponse> = arrayListOf()

        dateTickets.distinctBy { it.time }.forEach {
            responseList.add(ScheduleResponse("${it.time}:00", dateTickets.count { ticket -> ticket.time == it.time }))
        }

        return responseList
    }

    private fun getDateRange(date: LocalDate) =
        if (holidayRepository.findByDate(date) != null) HolidayTimeRange else CommonTimeRange

    fun getAvailableOrders(date: LocalDate): MutableList<ScheduleResponse> {
        val dateRange = getDateRange(date)
        val dateTickets = ticketRepository.findAllByOrderDate(date)
        val responseList: MutableList<ScheduleResponse> = arrayListOf()

        dateRange.forEach { time ->
            responseList.add(ScheduleResponse("${time}:00", MaxOrdersInHour - dateTickets.count { ticket ->
                ticket.time == time
            }))
        }

        return responseList
    }

    @Throws(Exception::class)
    fun reserveOrder(clientId: Int, date: LocalDate, time: Int): OrderInfo? {

        val client = clientRepository.findById(clientId)
        if (client.isEmpty)
            throw NoSuchElementFoundException("Client with given clientId doesn't exist")

        val dateOrders = orderRepository.findAllByDate(date)
        if (dateOrders.count { order -> order.client.id == clientId } == MaxOrdersInDayForSameClient)
            throw IllegalArgumentFoundException("Client already have maximum orders on given date")

        val dateRange = getDateRange(date)
        if (time !in dateRange)
            throw IllegalArgumentFoundException("Reserving time is out of schedule")

        val dateTickets = ticketRepository.findAllByOrderDate(date)
        if (dateTickets.count { ticket -> ticket.time == time } >= MaxOrdersInHour)
            throw IllegalArgumentFoundException("No more orders for given time (>$MaxOrdersInHour)")

        val newOrder = orderRepository.saveAndFlush(Order(-1, date, client.get()))
        val newTicket = Ticket(-1, time, newOrder)
        ticketRepository.save(newTicket)
        return OrderInfo(newOrder.orderId)

    }

    @Throws(Exception::class)
    fun reserveLongOrder(clientId: Int, date: LocalDate, time: Int, duration: Int): OrderInfo? {
        val client = clientRepository.findById(clientId)
        if (client.isEmpty) throw NoSuchElementFoundException("Client with given clientId doesn't exist")

        val dateOrders = orderRepository.findAllByDate(date)
        if (dateOrders.any { order -> order.client.id == clientId })
            throw IllegalArgumentFoundException("Client already have maximum orders on given date")
        if (duration <= 0)
            throw IllegalArgumentFoundException("Reserving duration value should be greater than 0")

        val dateRange = getDateRange(date)
        if (time !in dateRange)
            throw IllegalArgumentFoundException("Reserving time is out of schedule")
        if ((time + duration) !in dateRange)
            throw IllegalArgumentFoundException("Reserving duration doesn't fit schedule")

        val dateTickets = ticketRepository.findAllByOrderDate(date)
        if (!dateRange.all { timeTick -> dateTickets.count { ticket -> ticket.time == timeTick } < MaxOrdersInHour }) {
            throw IllegalArgumentFoundException("One or More of given time range is full of clients (>$MaxOrdersInHour)")
        }

        val newOrder = orderRepository.saveAndFlush(Order(-1, date, client.get()))
        (time until (time + duration)).forEach { timeTick ->
            val newTicket = Ticket(-1, timeTick, newOrder)
            ticketRepository.save(newTicket)
        }
        return OrderInfo(newOrder.orderId)
    }

    @Throws(NoSuchElementFoundException::class)
    fun cancelOrder(clientId: Int, orderId: Int) {
        val client = clientRepository.findById(clientId)
        if (client.isEmpty)
            throw NoSuchElementFoundException("Client with given clientId doesn't exist")

        val clientOrders = orderRepository.findAllByClientId(clientId)
        if (clientOrders.isEmpty())
            throw NoSuchElementFoundException("No order for this client found")
        if (clientOrders.all { order -> order.orderId != orderId })
            throw NoSuchElementFoundException("Client don't have given orderId")
        orderRepository.deleteById(orderId)
    }

    fun searchOrder(name: String?, date: LocalDate?): List<Order> {
        return if (name == null && date == null) {
            orderRepository.findAll()
        } else if (date == null) {
            orderRepository.findAllByClientName(name.toString())
        } else if (name == null) {
            orderRepository.findAllByDate(date)
        } else {
            orderRepository.findAllByClientNameAndDate(name.toString(), date)
        }
    }
}