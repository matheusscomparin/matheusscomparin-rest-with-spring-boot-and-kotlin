package com.hotmail.ma_adamo.repository

import com.hotmail.ma_adamo.model.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long?>