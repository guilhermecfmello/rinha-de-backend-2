package com.br.rinhadebackend2.crebito.models

import java.time.LocalDateTime

data class Transacao(
    val valor: Long,
    val tipo: String,
    val descricao: String,
    val realizadaEm: LocalDateTime?,
)