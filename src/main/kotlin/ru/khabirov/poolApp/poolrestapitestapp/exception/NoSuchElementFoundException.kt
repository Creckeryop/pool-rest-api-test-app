package ru.khabirov.poolApp.poolrestapitestapp.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class NoSuchElementFoundException(message: String) : RuntimeException(message)