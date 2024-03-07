package com.br.rinhadebackend2.crebito.adapters.repositories.models

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
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    val cliente: ClienteEntity? = null
) {
    constructor() : this(null, 0, "", "", null, null)
}
