package com.hotmail.ma_adamo.mapper.custom

import com.hotmail.ma_adamo.data.vo.v2.PersonVO
import com.hotmail.ma_adamo.model.Person
import org.springframework.stereotype.Service
import java.util.Date

@Service
class PersonMapper {

    fun mapEntityToV0(person: Person) : PersonVO {
        val vo = PersonVO()
        vo.firstName = person.firstName
        vo.lastName = person.lastName
        vo.address = person.address
        vo.gender = person.gender
        vo.birthDay = Date()
        vo.id   = person.id
        return vo
    }

    fun mapVOToEntity(person: PersonVO) : Person {
        val entity = Person()
        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender
        // entity.birthDay = Date()
        entity.id   = person.id
        return entity
    }
}