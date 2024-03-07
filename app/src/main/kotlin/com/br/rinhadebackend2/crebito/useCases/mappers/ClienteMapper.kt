package com.br.rinhadebackend2.crebito.useCases.mappers

import com.br.rinhadebackend2.crebito.adapters.repositories.models.ClienteEntity
import com.br.rinhadebackend2.crebito.models.Cliente

object ClienteMapper {

    fun from(clienteEntity: ClienteEntity) = Cliente(
        id = clienteEntity.id,
        nome = clienteEntity.nome,
        limite = clienteEntity.limite,
        saldoInicial = clienteEntity.saldoInicial
    )

    fun from(cliente: Cliente) = ClienteEntity(
        id = cliente.id,
        nome = cliente.nome,
        limite = cliente.limite,
        saldoInicial = cliente.saldoInicial
    )
}