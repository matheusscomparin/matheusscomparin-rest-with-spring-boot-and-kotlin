package com.hotmail.ma_adamo.mapper.custom

import com.hotmail.ma_adamo.data.vo.v1.BookVO
import com.hotmail.ma_adamo.data.vo.v2.PersonVO
import com.hotmail.ma_adamo.model.Book
import com.hotmail.ma_adamo.model.Person
import org.springframework.stereotype.Service
import java.util.Date

@Service
class BookMapper {

    fun mapEntityToV0(book: Book) : BookVO {
        val vo = BookVO()
        vo.author = book.author
        vo.title = book.title
        vo.price = book.price
        vo.launchDate = book.launchDate
        vo.key  = book.id
        return vo
    }

    fun mapVOToEntity(book: BookVO) : Book {
        val entity =Book()
        entity.author = book.author
        entity.title = book.title
        entity.price = book.price
        entity.launchDate = book.launchDate
        entity.id   = book.key
        return entity
    }
}