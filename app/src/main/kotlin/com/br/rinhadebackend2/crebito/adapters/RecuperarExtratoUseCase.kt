package com.br.rinhadebackend2.crebito.adapters

import com.br.rinhadebackend2.crebito.models.Extrato
import reactor.core.publisher.Mono

interface RecuperarExtratoUseCase {

    fun execute(idCliente: Int): Mono<Extrato>
}