package com.br.rinhadebackend2.crebito.adapters.controllers

import com.br.rinhadebackend2.crebito.adapters.CreditarUseCase
import com.br.rinhadebackend2.crebito.adapters.DebitarUseCase
import com.br.rinhadebackend2.crebito.adapters.RecuperarExtratoUseCase
import com.br.rinhadebackend2.crebito.exceptions.TransacaoInvalidaException
import com.br.rinhadebackend2.crebito.models.Extrato
import com.br.rinhadebackend2.crebito.models.Transacao
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
        // TODO: Fix empty extrato issue :(
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
        val transacao = Transacao(
            transacaoRequest.valor,
            transacaoRequest.tipo,
            transacaoRequest.descricao,
            null
        )
        when(transacao.tipo){
            "c" -> creditarUseCase.execute(idCliente, transacao)
            "d" -> debitarUseCase.execute(idCliente, transacao)
            else -> throw TransacaoInvalidaException(transacaoRequest.tipo)
        }
    }

}