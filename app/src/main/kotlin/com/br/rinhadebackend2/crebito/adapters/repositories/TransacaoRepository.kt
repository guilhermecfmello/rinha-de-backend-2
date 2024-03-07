package com.br.rinhadebackend2.crebito.adapters.repositories

import com.br.rinhadebackend2.crebito.adapters.repositories.models.TransacaoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TransacaoRepository: JpaRepository<TransacaoEntity, Long>{

    @Query("SELECT t FROM TransacaoEntity t WHERE t.cliente.id = :clienteId ORDER BY t.realizadaEm DESC LIMIT 10")
    fun findLast10TransactionsByClienteId(@Param("clienteId") clienteId: Int): List<TransacaoEntity>

}