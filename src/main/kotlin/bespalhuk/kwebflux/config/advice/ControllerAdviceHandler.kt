package bespalhuk.kwebflux.config.advice

import bespalhuk.kwebflux.core.common.exception.UserAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdviceHandler {

    @ExceptionHandler(Exception::class, Throwable::class)
    fun unknown(ex: Exception): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ex.message ?: "Unknown error")
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun alreadyExists(ex: Exception): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ex.message ?: "Unknown error")
    }
}
