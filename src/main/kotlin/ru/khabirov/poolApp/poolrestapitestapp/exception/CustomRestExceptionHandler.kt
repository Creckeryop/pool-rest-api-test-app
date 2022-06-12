package ru.khabirov.poolApp.poolrestapitestapp.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomRestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<String> {
        val violations = e.fieldErrors.map { it.defaultMessage }.toList().toString()
        return ResponseEntity("Not valid due to validation errors: $violations", HttpStatus.BAD_REQUEST)
    }
}