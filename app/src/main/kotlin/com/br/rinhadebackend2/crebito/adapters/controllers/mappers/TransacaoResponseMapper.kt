package com.br.rinhadebackend2.crebito.adapters.controllers.mappers

import com.br.rinhadebackend2.crebito.models.Cliente
import com.br.rinhadebackend2.crebito.models.TransacaoResponse

object TransacaoResponseMapper {

    fun from(cliente: Cliente) = TransacaoResponse(
        limite = cliente.limite!!,
        saldo = cliente.saldo!!
    )
}