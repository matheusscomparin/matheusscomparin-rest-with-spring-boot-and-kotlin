package com.hotmail.ma_adamo.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "books")
data class Book(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false, length = 100)
    var author: String = "",

    @Column(name = "launch_date")
    var launchDate: Date? = null,

    @Column(nullable = false)
    var price: Double = 0.0,

    @Column(nullable = false, length = 500)
    var title: String = ""
)