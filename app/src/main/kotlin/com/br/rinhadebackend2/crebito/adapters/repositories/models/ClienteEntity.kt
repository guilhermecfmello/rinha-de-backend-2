package com.br.rinhadebackend2.crebito.adapters.repositories.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "clientes")
data class ClienteEntity(
    @Id
    val id: Int,
    val nome: String? = null,
    val limite: Long? = null,
    val saldo: Long? = null
){
    constructor(): this(0)
}