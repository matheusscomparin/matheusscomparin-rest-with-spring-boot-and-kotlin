package com.hotmail.ma_adamo.services

import com.hotmail.ma_adamo.model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonServices {

    private val counter: AtomicLong = AtomicLong()

    private val logger = Logger.getLogger(PersonServices::class.java.name)

    fun findAll(): List<Person>{
        logger.info("Finding all people!")

        val persons: MutableList<Person> = ArrayList()
        for (i in 0..7){
            val person = mockPerson(i)
            persons.add(person)
        }
        return persons
    }

    fun findById(id: Long): Person{
        logger.info("Finding one person!")

        val person = Person()
        person.id = counter.incrementAndGet()
        person.firstName = "Matheus"
        person.lastName = "Scomparin"
        person.address = "Campinas - São Paulo - Brasil"
        person.gender = "Male"
        return person
    }

    fun create(person: Person) = person

    fun update(person: Person) = person

    fun delete(id: Long){}

    private fun mockPerson(i: Int): Person {
        val person = Person()
        person.id = counter.incrementAndGet()
        person.firstName = "Person Name $i"
        person.lastName = "Last Name $i"
        person.address = "Some Address in Brasil $i"
        person.gender = "Male"
        return person
    }

}