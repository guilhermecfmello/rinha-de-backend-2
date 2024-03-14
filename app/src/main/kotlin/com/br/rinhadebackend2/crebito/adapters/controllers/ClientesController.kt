package com.br.rinhadebackend2.crebito.adapters.controllers

import com.br.rinhadebackend2.crebito.adapters.CreditarUseCase
import com.br.rinhadebackend2.crebito.adapters.DebitarUseCase
import com.br.rinhadebackend2.crebito.adapters.RecuperarExtratoUseCase
import com.br.rinhadebackend2.crebito.adapters.controllers.mappers.TransacaoResponseMapper
import com.br.rinhadebackend2.crebito.exceptions.TransacaoInvalidaException
import com.br.rinhadebackend2.crebito.models.Extrato
import com.br.rinhadebackend2.crebito.models.Transacao
import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import com.br.rinhadebackend2.crebito.models.TransacaoResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class ClientesController(
    private val creditarUseCase: CreditarUseCase,
    private val debitarUseCase: DebitarUseCase,
    private val recuperarExtratoUseCase: RecuperarExtratoUseCase
) {

    private val transacaoMapper = TransacaoResponseMapper

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
    ): TransacaoResponse {
        val transacao = Transacao(
            transacaoRequest.valor,
            transacaoRequest.tipo!!,
            transacaoRequest.descricao!!,
            null
        )
        return when(transacao.tipo){
            "c" -> creditarUseCase.execute(idCliente, transacao).let { transacaoMapper.from(it) }
            "d" -> debitarUseCase.execute(idCliente, transacao).let { transacaoMapper.from(it) }
            else -> throw TransacaoInvalidaException(transacaoRequest.tipo)
        }
    }

}