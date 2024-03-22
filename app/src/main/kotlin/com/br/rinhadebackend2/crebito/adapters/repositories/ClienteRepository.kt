package com.br.rinhadebackend2.crebito.adapters.repositories

import com.br.rinhadebackend2.crebito.adapters.repositories.models.ClienteEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.query.Procedure
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ClienteRepository: JpaRepository<ClienteEntity, Int> {
    @Procedure(
        procedureName = "debitar_valor_no_cliente"
    )
    fun debitarValorNoCliente(
        @Param("id_cliente_arg") idCliente: Int,
        @Param("valor_transacao_arg") valorTransacao: Long,
        @Param("descricao_transacao_arg") descricaoTransacao: String,
        @Param("realizada_em_arg") realizadaEm: LocalDateTime,
        @Param("saldo_atualizado_return") saldoAtualizadoReturn: Int
    ): Int

    @Procedure(
        procedureName = "creditar_valor_no_cliente"
    )
    fun creditarValorNoCliente(
        @Param("idCliente") idCliente: Int,
        @Param("valorTransacao") valorTransacao: Long,
        @Param("descricaoTransacao") descricaoTransacao: String,
        @Param("realizadaEm") realizadaEm: LocalDateTime,
        @Param("saldo_atualizado_return") saldoAtualizadoReturn: Int
    ): Int
}