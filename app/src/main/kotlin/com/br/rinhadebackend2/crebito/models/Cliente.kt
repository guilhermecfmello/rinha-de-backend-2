package com.br.rinhadebackend2.crebito.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "clientes")
data class Cliente(
    @Id
    val id: Int,
    val nome: String? = null,
    val limite: Long? = null,
    // TODO("Change from saldoInicial to saldo")
    val saldo: Long? = null
){
    constructor(): this(0)
}