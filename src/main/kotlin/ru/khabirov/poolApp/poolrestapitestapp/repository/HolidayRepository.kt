package ru.khabirov.poolApp.poolrestapitestapp.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.khabirov.poolApp.poolrestapitestapp.models.Holiday
import java.time.LocalDate

@Repository
interface HolidayRepository : JpaRepository<Holiday, Int> {
    fun findByDate(date: LocalDate): Holiday?
    fun deleteByDate(date: LocalDate)
}