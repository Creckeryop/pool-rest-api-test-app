package ru.khabirov.poolApp.poolrestapitestapp.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.khabirov.poolApp.poolrestapitestapp.models.Client

@Repository
interface ClientRepository : JpaRepository<Client, Int>