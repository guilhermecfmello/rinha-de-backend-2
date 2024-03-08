package com.br.rinhadebackend2.crebito.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class Transacao(
    val valor: Long,
    val tipo: String,
    val descricao: String,
    @JsonProperty("realizada_em")
    val realizadaEm: LocalDateTime?,
)