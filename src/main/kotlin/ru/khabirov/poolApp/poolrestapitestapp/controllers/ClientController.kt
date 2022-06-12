package ru.khabirov.poolApp.poolrestapitestapp.controllers

import org.springframework.web.bind.annotation.*
import ru.khabirov.poolApp.poolrestapitestapp.models.Client
import ru.khabirov.poolApp.poolrestapitestapp.models.ClientInfo
import ru.khabirov.poolApp.poolrestapitestapp.service.PoolService
import javax.validation.Valid

@RestController
@RequestMapping("/api/v0/pool/client/")
class ClientController(private val poolService: PoolService) {

    @GetMapping("/all")
    fun getClients(): List<ClientInfo> {
        return poolService.getAllClients()
    }

    @GetMapping("/get/{id}")
    fun getClient(@PathVariable id: Int): Client? {
        return poolService.getClient(id)
    }

    @PostMapping("/add")
    fun addClient(@RequestBody @Valid client: Client) {
        poolService.addClient(client)
    }

    @PostMapping("/update")
    fun updateClient(@RequestBody @Valid client: Client) {
        poolService.updateClient(client)
    }

}