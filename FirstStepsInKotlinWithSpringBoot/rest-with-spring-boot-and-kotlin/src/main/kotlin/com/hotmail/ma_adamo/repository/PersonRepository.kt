package com.hotmail.ma_adamo.repository

import com.hotmail.ma_adamo.model.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository : JpaRepository<Person, Long?>