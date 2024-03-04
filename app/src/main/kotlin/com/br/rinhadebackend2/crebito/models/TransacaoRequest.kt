package com.br.rinhadebackend2.crebito.models

data class TransacaoRequest(
    val valor: Int,
    val tipo: String,
    val descricao: String
)