package com.br.rinhadebackend2.crebito.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class Saldo(
    val total: Long,
    val limite: Long,
    @JsonProperty("data_extrato")
    val dataExtrato: LocalDateTime
)
