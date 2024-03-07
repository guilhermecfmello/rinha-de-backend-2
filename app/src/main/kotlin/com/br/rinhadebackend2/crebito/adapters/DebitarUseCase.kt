package com.br.rinhadebackend2.crebito.adapters

import com.br.rinhadebackend2.crebito.models.Cliente
import com.br.rinhadebackend2.crebito.models.Transacao

interface DebitarUseCase {

    fun execute(idCliente: Int, transacao: Transacao): Cliente
}