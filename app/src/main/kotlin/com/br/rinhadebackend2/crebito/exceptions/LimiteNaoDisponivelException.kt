package com.br.rinhadebackend2.crebito.exceptions

class LimiteNaoDisponivelException(
    val valor: Long,
    val idCliente: Int
): RuntimeException() {

}