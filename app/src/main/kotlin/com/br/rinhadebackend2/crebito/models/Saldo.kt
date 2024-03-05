package com.br.rinhadebackend2.crebito.models

import java.time.LocalDateTime

data class Saldo(
    val total: Long,
    val limite: Long,
    val dataExtrato: LocalDateTime
)
