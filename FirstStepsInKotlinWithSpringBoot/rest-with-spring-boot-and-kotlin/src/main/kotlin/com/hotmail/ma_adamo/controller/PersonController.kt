package com.hotmail.ma_adamo.controller

import com.hotmail.ma_adamo.converter.NumberConverter
import com.hotmail.ma_adamo.exceptions.UnsupportedMathOperationException
import com.hotmail.ma_adamo.math.SimpleMath
import com.hotmail.ma_adamo.model.Person
import com.hotmail.ma_adamo.services.PersonServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController {

    @Autowired
    private lateinit var service: PersonServices

    @RequestMapping(method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): List<Person> {
        return service.findAll()
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET],
                    produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable(value="id") id: Long
    ): Person {
       return service.findById(id)
    }

    @RequestMapping(method = [RequestMethod.POST],
                    consumes = [MediaType.APPLICATION_JSON_VALUE],
                    produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody person: Person): Person {
       return service.create(person)
    }

    @RequestMapping(method = [RequestMethod.PUT],
                    consumes = [MediaType.APPLICATION_JSON_VALUE],
                    produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@RequestBody person: Person): Person {
       return service.update(person)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable(value="id") id: Long) {
        service.delete(id)
    }

}