package com.hotmail.ma_adamo.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "books")
class Book(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false, length = 100)
    var author: String = "",

    @Column(name = "launch_date", nullable = false, length = 100)
    var launchDate: Date,

    @Column(nullable = false, length = 100)
    var price: Double,

    @Column(nullable = false, length = 500)
    var title: String = ""
)