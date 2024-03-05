package com.br.rinhadebackend2.crebito.adapters.controllers

import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.models.TransacaoEntity
import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientesController(
    private val dateTimeProvider: DateTimeProvider,
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository
) {

    @GetMapping("/clientes/{idCliente}/extrato")
    fun getExtrato(
        @PathVariable
        idCliente: Int
    ) {
        try{
            val transacoes = transacaoRepository.findAll()
            println(transacoes)
        } catch (e: Exception){
            println(e)
        }
    }

    @PostMapping("/clientes/{idCliente}/transacoes")
    fun createTransacao(
        @PathVariable
        idCliente: Int,
        @RequestBody
        transacaoRequest: TransacaoRequest
    ) {
        val transacaoEntity = TransacaoEntity(
            valor = transacaoRequest.valor,
            tipo = transacaoRequest.tipo,
            descricao = transacaoRequest.descricao,
            idCliente = idCliente,
            realizadaEm = dateTimeProvider.instante()
        )
        transacaoRepository.save(transacaoEntity)
    }

}