package com.br.rinhadebackend2.crebito.models

data class Saldo(
    val total: Long,
    val limite: Long,
    val dataExtrato: String?
)
