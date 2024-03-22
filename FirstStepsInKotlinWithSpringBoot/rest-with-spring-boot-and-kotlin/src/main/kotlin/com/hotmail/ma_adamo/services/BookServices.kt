package com.hotmail.ma_adamo.services

import com.hotmail.ma_adamo.controller.BookController
import com.hotmail.ma_adamo.data.vo.v1.BookVO
import com.hotmail.ma_adamo.exceptions.RequiredObjectIsNullException
import com.hotmail.ma_adamo.exceptions.ResourceNotFoundException
import com.hotmail.ma_adamo.mapper.DozerMapper
import com.hotmail.ma_adamo.mapper.custom.BookMapper
import com.hotmail.ma_adamo.model.Book
import com.hotmail.ma_adamo.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookServices {

    @Autowired
    private lateinit var repository: BookRepository

    @Autowired
    private lateinit var mapper: BookMapper

    private val logger = Logger.getLogger(BookServices::class.java.name)

    fun findAll(): List<BookVO>{
        logger.info("Finding all books")
        val books = repository.findAll()
        val bookVO: List<BookVO> = DozerMapper.parseListObject(books, BookVO::class.java)
        bookVO
            .stream()
            .forEach { p ->
                val withSelfRel = linkTo(BookController::class.java).slash(p.key).withSelfRel()
                p.add(withSelfRel)
            }
        return bookVO
    }

    fun findById(id: Long): BookVO{
        logger.info("Finding one book with id: $id")
        var book = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID")}
        val bookVO: BookVO = DozerMapper.parseObject(book, BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun create(book: BookVO?) : BookVO{
        if (book == null) throw RequiredObjectIsNullException()
        logger.info("Creating one book with title ${book.title}")
        var entity: Book = DozerMapper.parseObject(book, Book::class.java)
        val bookVO: BookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    // Versionando endpoint
    /*
    fun createV2(person: PersonVOV2) : PersonVOV2{
        logger.info("Creating one person with name ${person.firstName}")
        var entity: Person = mapper.mapVOToEntity(person)
        return mapper.mapEntityToV0(repository.save(entity))
    }*/

    fun update(book: BookVO?) : BookVO{
        if (book == null) throw RequiredObjectIsNullException()
        logger.info("Updating one book with id ${book.key}")
        val entity =  repository.findById(book.key)
            .orElseThrow { ResourceNotFoundException("No records found for this ID")}

        entity.author = book.author
        entity.launchDate = book.launchDate
        entity.price = book.price
        entity.title = book.title
        val bookVO: BookVO = DozerMapper.parseObject(repository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun delete(id: Long){
        logger.info("Deleting one book with id: $id")
        val entity =  repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID")}
        repository.delete(entity)
    }
}