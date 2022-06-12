package ru.khabirov.poolApp.poolrestapitestapp.models

import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Validated
@Table(name = "holiday")
class Holiday(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @NotBlank
    @Column(name = "date", unique = true)
    var date: LocalDate
)