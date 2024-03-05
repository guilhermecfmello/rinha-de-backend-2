package com.br.rinhadebackend2.crebito.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "transacoes")
data class TransacaoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val valor: Long,
    val tipo: String,
    val descricao: String,
    val realizadaEm: LocalDateTime? = null,
    val idCliente: Int
) {
    // Default constructor
    constructor() : this(null, 0, "", "", null, 0)
}
