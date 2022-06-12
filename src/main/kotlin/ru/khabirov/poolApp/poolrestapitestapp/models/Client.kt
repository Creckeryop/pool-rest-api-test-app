package ru.khabirov.poolApp.poolrestapitestapp.models

import org.springframework.validation.annotation.Validated
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Validated
@Table(name = "client")
class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = -1,

    @field: NotEmpty(message = "\"name\" is required")
    @field: Size(min = 1, message = "Name is too small")
    @Column(name = "name")
    var name: String = "",

    @field: NotEmpty(message = "\"phone\" is required")
    @field: Pattern(regexp = "^\\+?\\d{11}$", message = "Incorrect phone")
    @Column(name = "phone", unique = true, length = 12)
    var phone: String = "",

    @field: NotEmpty(message = "\"email\" is required")
    @field: Size(min = 5, message = "Email is too small")
    @field: Pattern(regexp = "^\\w+@\\w+\\.\\w+$", message = "Incorrect email")
    @Column(name = "email", unique = true)
    var email: String = "",
)