package com.br.rinhadebackend2.crebito.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "clientes")
data class ClienteEntity(
    @Id
    val id: Int,
    val nome: String,
    val limite: Long,
    val saldoInicial: Long
)