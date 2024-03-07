package com.br.rinhadebackend2.crebito.adapters.controllers

import com.br.rinhadebackend2.crebito.adapters.CreditarUseCase
import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
import com.br.rinhadebackend2.crebito.adapters.DebitarUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.models.Extrato
import com.br.rinhadebackend2.crebito.models.Saldo
import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import org.springframework.web.bind.annotation.*

@RestController
class ClientesController(
    private val dateTimeProvider: DateTimeProvider,
    private val transacaoRepository: TransacaoRepository,
    private val creditarUseCase: CreditarUseCase,
    private val debitarUseCase: DebitarUseCase
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
        when(transacaoRequest.tipo){
            "c" -> creditarUseCase.execute(idCliente, transacaoRequest)
            "d" -> debitarUseCase.execute(idCliente, transacaoRequest)
        }
    }

}