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
    val saldo: Long? = null
)