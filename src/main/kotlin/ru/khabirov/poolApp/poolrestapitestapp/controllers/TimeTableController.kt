package ru.khabirov.poolApp.poolrestapitestapp.controllers

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.khabirov.poolApp.poolrestapitestapp.models.*
import ru.khabirov.poolApp.poolrestapitestapp.service.PoolService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.Valid
import javax.validation.constraints.Pattern

@RestController
@Validated
@RequestMapping("/api/v0/pool/timetable/")
class TimeTableController(private val poolService: PoolService) {

    @GetMapping("/all/{dateString}")
    fun getAll(
        @PathVariable @Pattern(
            regexp = "^\\d\\d\\d\\d-\\d\\d-\\d\\d$", message = "Incorrect date"
        ) @Valid dateString: String
    ): MutableList<ScheduleResponse> {
        return poolService.getAllOrders(LocalDate.parse(dateString))
    }

    @GetMapping("/available/{dateString}")
    fun getAvailable(
        @PathVariable @Pattern(
            regexp = "^\\d\\d\\d\\d-\\d\\d-\\d\\d$", message = "Incorrect date"
        ) @Valid dateString: String
    ): MutableList<ScheduleResponse> {
        return poolService.getAvailableOrders(LocalDate.parse(dateString))
    }

    @PostMapping("/reserve")
    fun reserve(@RequestBody @Valid reserveRequest: ReserveRequest): OrderInfo? {
        val date = LocalDateTime.parse(reserveRequest.dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        return if (reserveRequest.duration == 0) {
            poolService.reserveOrder(reserveRequest.clientId, date.toLocalDate(), date.hour)
        } else {
            poolService.reserveLongOrder(
                reserveRequest.clientId, date.toLocalDate(), date.hour, reserveRequest.duration
            )
        }
    }

    @PostMapping("/cancel")
    fun cancel(@RequestBody cancelOrderRequest: CancelOrderRequest) {
        poolService.cancelOrder(cancelOrderRequest.clientId, cancelOrderRequest.orderId)
    }

    @GetMapping("/search")
    fun search(
        @RequestParam(name = "name", defaultValue = "") name: String,
        @RequestParam(name = "date", defaultValue = "") date: String
    ): List<Order> {
        val searchName: String? = if (name == "") null else name
        val searchDate: LocalDate? = if (date == "") null
        else LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return poolService.searchOrder(searchName, searchDate)
    }
}