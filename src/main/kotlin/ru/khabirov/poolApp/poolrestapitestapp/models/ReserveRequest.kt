package ru.khabirov.poolApp.poolrestapitestapp.models

import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Pattern
import javax.validation.constraints.Positive

@Validated
class ReserveRequest(
    val clientId: Int,
    @field:Pattern(
        regexp = "^\\d\\d\\d\\d-\\d\\d-\\d\\d \\d?\\d:\\d\\d$",
        message = "Incorrect date"
    )
    val dateTime: String,
    @field:Positive(message = "Duration value should be positive")
    val duration: Int = 1
)