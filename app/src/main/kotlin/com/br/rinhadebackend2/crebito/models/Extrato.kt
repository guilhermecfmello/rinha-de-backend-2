package com.br.rinhadebackend2.crebito.models

data class Extrato(
    val saldo: Saldo,
    val ultimasTransacoes: ArrayList<TransacaoCompleta>
)