package ru.khabirov.poolApp.poolrestapitestapp.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class IllegalArgumentFoundException(message: String) : RuntimeException(message)