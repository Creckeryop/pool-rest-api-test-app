package ru.khabirov.poolApp.poolrestapitestapp.controllers

import org.springframework.web.bind.annotation.*
import ru.khabirov.poolApp.poolrestapitestapp.models.Holiday
import ru.khabirov.poolApp.poolrestapitestapp.service.PoolService

@RestController
@RequestMapping("/api/v0/pool/holiday/")
class HolidayController(private val poolService: PoolService) {

    @PostMapping("/add")
    fun add(@RequestBody holiday: Holiday) {
        poolService.addHoliday(holiday.date)
    }

    @GetMapping("/all")
    fun getAll(): List<Holiday> {
        return poolService.getHolidays()
    }

    @PostMapping("/remove")
    fun remove(@RequestBody holiday: Holiday) {
        poolService.removeHoliday(holiday.date)
    }
}