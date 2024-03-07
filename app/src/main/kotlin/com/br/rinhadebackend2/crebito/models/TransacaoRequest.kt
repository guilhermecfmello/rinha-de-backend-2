package com.br.rinhadebackend2.crebito.models

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class TransacaoRequest(
    @field:Positive(message = "Valor deve ser maior que zero")
    val valor: Long,
    @field:NotBlank(message = "Tipo deve ser informado")
    val tipo: String,
    @field:Size(max = 10, message = "Descricao deve ter tamanho ate 10")
    val descricao: String
)