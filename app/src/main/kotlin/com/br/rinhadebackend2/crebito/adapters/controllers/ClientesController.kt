package com.br.rinhadebackend2.crebito.adapters.controllers

import com.br.rinhadebackend2.crebito.models.Extrato
import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import com.br.rinhadebackend2.crebito.models.TransacaoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientesController {

    @GetMapping("/clientes/{idCliente}/extrato")
    fun getExtrato(
        @RequestParam
        idCliente: Int
    ): Extrato {
        TODO("Implement here")

    }

    @PostMapping("/clientes/{idCliente}/transacoes")
    fun createTrasacao(
        @RequestParam
        idCliente: Int,
        @RequestBody
        transacaoRequest: TransacaoRequest
    ): TransacaoResponse {
        TODO("Implement here")
    }

}