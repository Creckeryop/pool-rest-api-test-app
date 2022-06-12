package ru.khabirov.poolApp.poolrestapitestapp.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Future
import javax.validation.constraints.NotNull

@Entity
@Validated
@Table(name = "pool_order")
class Order(
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderId: Int = -1,

    @NotNull
    @Future
    @Column(name = "date")
    var date: LocalDate = LocalDate.now(),

    @NotNull
    @OneToOne
    @JoinColumn(name = "client_id")
    var client: Client = Client(),

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var tickets: MutableList<Ticket> = arrayListOf()
)