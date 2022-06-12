package ru.khabirov.poolApp.poolrestapitestapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PoolAppApplication

fun main(args: Array<String>) {
	runApplication<PoolAppApplication>(*args)
}
