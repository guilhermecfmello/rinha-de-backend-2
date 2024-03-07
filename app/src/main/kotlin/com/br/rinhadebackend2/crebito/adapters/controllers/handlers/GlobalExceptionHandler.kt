package com.br.rinhadebackend2.crebito.adapters.controllers.handlers

import com.br.rinhadebackend2.crebito.exceptions.LimiteNaoDisponivelException
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFound(e: EntityNotFoundException): ResponseEntity<String> {
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(LimiteNaoDisponivelException::class)
    fun handleLimiteNaoDisponivel(e: LimiteNaoDisponivelException): ResponseEntity<String> {
        return ResponseEntity.unprocessableEntity().body("Valor ${e.valor} excede limite do cliente ${e.idCliente} ")
    }
}