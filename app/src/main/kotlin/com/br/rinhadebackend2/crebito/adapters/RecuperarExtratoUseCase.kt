package com.br.rinhadebackend2.crebito.adapters

import com.br.rinhadebackend2.crebito.models.Extrato

interface RecuperarExtratoUseCase {

    fun execute(idCliente: Int): Extrato
}