package com.br.rinhadebackend2.crebito.adapters.controllers

import com.br.rinhadebackend2.crebito.adapters.CreditarUseCase
import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.models.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientesController(
    private val dateTimeProvider: DateTimeProvider,
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository,
    private val creditarUseCase: CreditarUseCase
) {

    @GetMapping("/clientes/{idCliente}/extrato")
    fun getExtrato(
        @PathVariable
        idCliente: Int
    ): Extrato {
       return try{
            val transacoes = transacaoRepository.findLast10TransactionsByClienteId(clienteId = idCliente)
            val extrato = Extrato(
                saldo = Saldo(
                    total = transacoes[0].cliente!!.limite!!,
                    limite = transacoes[0].cliente!!.limite!!,
                    dataExtrato = dateTimeProvider.instante()
                ),
                ultimasTransacoes = transacoes
            )
           extrato
        } catch (e: Exception){
            throw e
        }
    }

    @PostMapping("/clientes/{idCliente}/transacoes")
    fun createTransacao(
        @PathVariable
        idCliente: Int,
        @RequestBody
        transacaoRequest: TransacaoRequest
    ) {
        try{
            when(transacaoRequest.tipo){
                "c" -> creditarUseCase.execute(idCliente, transacaoRequest)
                "d" -> TODO("")
            }
        } catch (e: Exception){
            // TODO: Deal with errors
            throw e
        }
    }

}