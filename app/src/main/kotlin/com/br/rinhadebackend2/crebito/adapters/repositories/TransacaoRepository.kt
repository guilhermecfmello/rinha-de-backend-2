package com.br.rinhadebackend2.crebito.adapters.repositories

import com.br.rinhadebackend2.crebito.models.TransacaoCompleta
import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import com.br.rinhadebackend2.crebito.models.TransacaoResponse
import org.springframework.stereotype.Repository

@Repository
interface TransacaoRepository {

    fun adicionarTransacao(idCliente: Int, transacao: TransacaoRequest): TransacaoResponse

    fun recuperarTransacoes(idCliente: Int): List<TransacaoCompleta>
}