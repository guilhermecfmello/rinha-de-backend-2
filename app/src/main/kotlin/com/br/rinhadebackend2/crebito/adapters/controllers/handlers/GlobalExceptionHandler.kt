package com.br.rinhadebackend2.crebito.adapters.controllers.handlers

import com.br.rinhadebackend2.crebito.exceptions.LimiteNaoDisponivelException
import com.br.rinhadebackend2.crebito.exceptions.TransacaoInvalidaException
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleNotFoundException(e: EntityNotFoundException): ResponseEntity<String> {
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(LimiteNaoDisponivelException::class)
    fun handleLimiteNaoDisponivelException(e: LimiteNaoDisponivelException): ResponseEntity<String> {
        return ResponseEntity.unprocessableEntity().body("Valor ${e.valor} excede limite do cliente ${e.idCliente} ")
    }

    @ExceptionHandler(TransacaoInvalidaException::class)
    fun handleTransacaoInvalidaException(e: TransacaoInvalidaException): ResponseEntity<String> {
        return ResponseEntity.unprocessableEntity().body("Transacao invalida : ${e.transacao}")
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<String>{
        return ResponseEntity.unprocessableEntity().body("Erro na validacao do seu request")
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<String>{
        return ResponseEntity.unprocessableEntity().body("Valor int nao pode ser passado como float.")
    }
}