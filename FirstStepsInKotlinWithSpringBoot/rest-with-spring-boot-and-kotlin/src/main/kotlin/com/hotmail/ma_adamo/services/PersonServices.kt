package com.hotmail.ma_adamo.services

import com.hotmail.ma_adamo.controller.PersonController
import com.hotmail.ma_adamo.data.vo.v1.PersonVO
import com.hotmail.ma_adamo.exceptions.RequiredObjectIsNullException
import com.hotmail.ma_adamo.data.vo.v2.PersonVO as PersonVOV2
import com.hotmail.ma_adamo.exceptions.ResourceNotFoundException
import com.hotmail.ma_adamo.mapper.DozerMapper
import com.hotmail.ma_adamo.mapper.custom.PersonMapper
import com.hotmail.ma_adamo.model.Person
import com.hotmail.ma_adamo.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonServices {

    @Autowired
    private lateinit var repository: PersonRepository

    @Autowired
    private lateinit var mapper: PersonMapper

    private val logger = Logger.getLogger(PersonServices::class.java.name)

    fun findAll(): List<PersonVO>{
        logger.info("Finding all people")
        val persons = repository.findAll()
        val personVO: List<PersonVO> = DozerMapper.parseListObject(persons, PersonVO::class.java)
        personVO
            .stream()
            .forEach { p ->
                val withSelfRel = linkTo(PersonController::class.java).slash(p.key).withSelfRel()
                p.add(withSelfRel)
            }
        return personVO
    }

    fun findById(id: Long): PersonVO{
        logger.info("Finding one person with $id")
        var person = repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID")}
        val personVO: PersonVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun create(person: PersonVO?) : PersonVO{
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Creating one person with name ${person.firstName}")
        var entity: Person = DozerMapper.parseObject(person, Person::class.java)
        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    // Versionando endpoint
    /*
    fun createV2(person: PersonVOV2) : PersonVOV2{
        logger.info("Creating one person with name ${person.firstName}")
        var entity: Person = mapper.mapVOToEntity(person)
        return mapper.mapEntityToV0(repository.save(entity))
    }*/

    fun update(person: PersonVO?) : PersonVO{
        if (person == null) throw RequiredObjectIsNullException()
        logger.info("Updating one person with id ${person.key}")
        val entity =  repository.findById(person.key)
            .orElseThrow { ResourceNotFoundException("No records found for this ID")}

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
        val personVO: PersonVO = DozerMapper.parseObject(repository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun delete(id: Long){
        logger.info("Deleting one person with name $id")
        val entity =  repository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this ID")}
        repository.delete(entity)
    }
}