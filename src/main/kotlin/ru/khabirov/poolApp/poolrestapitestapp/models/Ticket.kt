package ru.khabirov.poolApp.poolrestapitestapp.models

import org.springframework.validation.annotation.Validated
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Validated
@Table(name = "ticket")
class Ticket(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = -1,

    @NotNull
    @Column(name = "time")
    var time: Int = 0,

    @ManyToOne(optional = false)
    @JoinColumn(name="order_id")
    var order: Order = Order()
)