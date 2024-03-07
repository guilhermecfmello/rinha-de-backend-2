package com.br.rinhadebackend2.crebito.adapters.repositories

import com.br.rinhadebackend2.crebito.models.Extrato

interface RecuperarExtratoUseCase {

    fun execute(idCliente: Int): Extrato
}