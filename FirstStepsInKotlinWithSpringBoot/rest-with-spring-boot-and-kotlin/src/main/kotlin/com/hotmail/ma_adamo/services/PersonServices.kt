package com.hotmail.ma_adamo.services

import com.hotmail.ma_adamo.data.vo.v1.PersonVO
import com.hotmail.ma_adamo.exceptions.ResourceNotFoundException
import com.hotmail.ma_adamo.mapper.DozerMapper
import com.hotmail.ma_adamo.model.Person
import com.hotmail.ma_adamo.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonServices {

    @Autowired
    private lateinit var repository: PersonRepository

    private val logger = Logger.getLogger(PersonServices::class.java.name)

    fun findAll(): List<PersonVO>{
        logger.info("Finding all people")
        val persons = repository.findAll()
        return DozerMapper.parseListObject(persons, PersonVO::class.java)
    }

    fun findById(id: Long): PersonVO{
        logger.info("Finding one person")
        var person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID")}
        return DozerMapper.parseObject(person, PersonVO::class.java)
    }

    fun create(person: PersonVO) : PersonVO{
        logger.info("Creating one person with name ${person.firstName}")
        var entity: Person = DozerMapper.parseObject(person, Person::class.java)
        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun update(person: PersonVO) : PersonVO{
        logger.info("Updating one person with id ${person.id}")
        val entity =  repository.findById(person.id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID")}

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
        return DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
    }

    fun delete(id: Long){
        logger.info("Deleting one person with name ${id}")
        val entity =  repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID")}
        repository.delete(entity)
    }
}