package com.hotmail.ma_adamo.unittests.mapper.mocks

import com.hotmail.ma_adamo.data.vo.v1.BookVO
import com.hotmail.ma_adamo.model.Book
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class MockBook {
    fun mockEntity(): Book {
        return mockEntity(0)
    }

    fun mockVO(): BookVO {
        return mockVO(0)
    }

    fun mockEntityList(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList<Book>()
        for (i in 0..13) {
            books.add(mockEntity(i))
        }
        return books
    }

    fun mockVOList(): ArrayList<BookVO> {
        val books: ArrayList<BookVO> = ArrayList()
        for (i in 0..13) {
            books.add(mockVO(i))
        }
        return books
    }

    fun mockEntity(number: Int): Book {
        val book = Book()
        book.author = "Author Test$number"
        book.title  = "Title Test$number"
        book.price = (10 * number).toDouble()
        book.id = number.toLong()
        book.launchDate =  Date.from(LocalDate.now().plusDays(number.toLong()).atStartOfDay(ZoneId.systemDefault()).toInstant())
        return book
    }

    fun mockVO(number: Int): BookVO {
        val book = BookVO()
        book.author = "Author Test$number"
        book.title  = "Title Test$number"
        book.price = (10 * number).toDouble()
        book.key = number.toLong()
        book.launchDate = Date.from(LocalDate.now().plusDays(number.toLong()).atStartOfDay(ZoneId.systemDefault()).toInstant())
        return book
    }
}