package com.hotmail.ma_adamo.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequiredObjectIsNullException : RuntimeException {
    constructor(): super("It's not allowed to persist a null objetct")
    constructor(exception: String?): super(exception)
}