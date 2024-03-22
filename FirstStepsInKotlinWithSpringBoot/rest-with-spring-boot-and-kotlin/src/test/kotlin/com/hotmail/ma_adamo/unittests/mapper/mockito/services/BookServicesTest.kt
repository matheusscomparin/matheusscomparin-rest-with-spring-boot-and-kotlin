package com.hotmail.ma_adamo.unittests.mapper.mockito.services

import com.hotmail.ma_adamo.unittests.mapper.mocks.MockBook
import com.hotmail.ma_adamo.exceptions.RequiredObjectIsNullException
import com.hotmail.ma_adamo.repository.BookRepository
import com.hotmail.ma_adamo.services.BookServices
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookServicesTest {

    private lateinit var inputObject: MockBook

    @InjectMocks
    private lateinit var service: BookServices

    @Mock
    private lateinit var repository: BookRepository

    @BeforeEach
    fun setUpMock() {
        inputObject = MockBook()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val list = inputObject.mockEntityList()
        `when`(repository.findAll()).thenReturn(list)

        val books = service.findAll()

        assertNotNull(books)
        assertEquals(14, books.size)

        val bookOne = books[1]
        assertNotNull(bookOne)
        assertNotNull(bookOne.key)
        assertNotNull(bookOne.links)
        assertTrue(bookOne.links.toString().contains("</api/Book/v1/1>;rel=\"self\""))
        assertEquals("Author Test1", bookOne.author)
        assertEquals("Title Test1", bookOne.title)
        assertEquals(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), bookOne.launchDate)
        assertEquals(10.0, bookOne.price)

        val bookFour = books[4]
        assertNotNull(bookFour)
        assertNotNull(bookFour.key)
        assertNotNull(bookFour.links)
        assertTrue(bookFour.links.toString().contains("</api/Book/v1/4>;rel=\"self\""))
        assertEquals("Author Test4", bookFour.author)
        assertEquals("Title Test4", bookFour.title)
        assertEquals(Date.from(LocalDate.now().plusDays(4).atStartOfDay(ZoneId.systemDefault()).toInstant()), bookFour.launchDate)
        assertEquals(40.0, bookFour.price)

        val bookSeven = books[7]
        assertNotNull(bookSeven)
        assertNotNull(bookSeven.key)
        assertNotNull(bookSeven.links)
        assertTrue(bookSeven.links.toString().contains("</api/Book/v1/7>;rel=\"self\""))
        assertEquals("Author Test7", bookSeven.author)
        assertEquals("Title Test7", bookSeven.title)
        assertEquals(Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant()), bookSeven.launchDate)
        assertEquals(70.0, bookSeven.price)

    }

    @Test
    fun findById() {
        val book = inputObject.mockEntity(1)
        book.id = 1L
        `when`(repository.findById(1)).thenReturn(Optional.of(book))

        val result = service.findById(1)
        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertEquals(result.key, book.id)
        assertTrue(result.links.toString().contains("</api/Book/v1/1>;rel=\"self\""))
        assertEquals("Author Test1", result.author)
        assertEquals("Title Test1", result.title)
        assertEquals(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), result.launchDate)
        assertEquals(10.0, result.price)
    }

    @Test
    fun createWithNullBook() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ) {service.create(null)}

        val expectedMessage = "It's not allowed to persist a null objetct"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun create() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1L

        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.create(vo)
        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        println(result.links)
        assertTrue(result.links.toString().contains("</api/Book/v1/1>;rel=\"self\""))
        assertEquals(result.key, persisted.id)
        assertEquals("Author Test1", result.author)
        assertEquals("Title Test1", result.title)
        assertEquals(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), result.launchDate)
        assertEquals(10.0, result.price)
    }

    @Test
    fun updateWithNullBook() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ) {service.update(null)}

        val expectedMessage = "It's not allowed to persist a null objetct"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun update() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1L

        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.update(vo)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/Book/v1/1>;rel=\"self\""))
        assertEquals(result.key, persisted.id)
        assertEquals("Author Test1", result.author)
        assertEquals("Title Test1", result.title)
        assertEquals(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()), result.launchDate)
        assertEquals(10.0, result.price)
    }

    @Test
    fun delete() {
        val entity = inputObject.mockEntity(1)
        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        service.delete(1)
    }
}