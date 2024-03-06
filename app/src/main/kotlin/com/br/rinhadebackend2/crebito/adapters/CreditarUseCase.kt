package com.br.rinhadebackend2.crebito.adapters

import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import com.br.rinhadebackend2.crebito.models.TransacaoResponse

interface CreditarUseCase {

    fun execute(idCliente: Int, transacaoRequest: TransacaoRequest): TransacaoResponse
}