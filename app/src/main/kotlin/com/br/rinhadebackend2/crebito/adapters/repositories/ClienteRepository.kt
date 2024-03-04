package com.br.rinhadebackend2.crebito.adapters.repositories

import com.br.rinhadebackend2.crebito.models.Extrato
import org.springframework.stereotype.Repository

@Repository
interface ClienteRepository {

    fun getSaldo(idCliente: Int): Extrato
}