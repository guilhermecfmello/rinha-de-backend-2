package com.br.rinhadebackend2.crebito.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Extrato(
    val saldo: Saldo,
    @JsonProperty("ultimas_transacoes")
    val ultimasTransacoes: List<Transacao>
)