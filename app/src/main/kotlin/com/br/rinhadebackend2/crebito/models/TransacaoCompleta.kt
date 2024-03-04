package com.br.rinhadebackend2.crebito.models

data class TransacaoCompleta(
    val valor: Long,
    val tipo: String,
    val descricao: String,
    val realizadaEm: String?
)