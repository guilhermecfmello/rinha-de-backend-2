package com.br.rinhadebackend2.crebito.adapters.controllers

import com.br.rinhadebackend2.crebito.adapters.CreditarUseCase
import com.br.rinhadebackend2.crebito.adapters.DebitarUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.RecuperarExtratoUseCase
import com.br.rinhadebackend2.crebito.exceptions.TransacaoInvalidaException
import com.br.rinhadebackend2.crebito.models.Extrato
import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class ClientesController(
    private val creditarUseCase: CreditarUseCase,
    private val debitarUseCase: DebitarUseCase,
    private val recuperarExtratoUseCase: RecuperarExtratoUseCase
) {

    @GetMapping("/clientes/{idCliente}/extrato")
    fun getExtrato(
        @PathVariable
        idCliente: Int
    ): Extrato {
       return recuperarExtratoUseCase.execute(idCliente)
    }

    @PostMapping("/clientes/{idCliente}/transacoes")
    fun createTransacao(
        @PathVariable
        idCliente: Int,
        @RequestBody
        @Valid
        transacaoRequest: TransacaoRequest
    ) {
        when(transacaoRequest.tipo){
            "c" -> creditarUseCase.execute(idCliente, transacaoRequest)
            "d" -> debitarUseCase.execute(idCliente, transacaoRequest)
            else -> throw TransacaoInvalidaException(transacaoRequest.tipo)
        }
    }

}