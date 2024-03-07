package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.CreditarUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.models.TransacaoEntity
import com.br.rinhadebackend2.crebito.models.Cliente
import com.br.rinhadebackend2.crebito.models.Transacao
import com.br.rinhadebackend2.crebito.models.TransacaoResponse
import com.br.rinhadebackend2.crebito.useCases.mappers.ClienteMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component

@Component
class CreditarUseCaseImpl(
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository
) : CreditarUseCase {

    private val clienteMapper = ClienteMapper

    override fun execute(idCliente: Int, transacao: Transacao): Cliente {
        val cliente = buscaClientePorId(idCliente)

        val clienteComSaldoAtualizado = atualizarSaldoCliente(cliente, transacao.valor)
        val novaTransacaoEntity = criarNovaTransacao(transacao)

        salvarCliente(clienteComSaldoAtualizado)
        salvarTransacao(novaTransacaoEntity)

        return clienteComSaldoAtualizado
    }

    private fun buscaClientePorId(idCliente: Int) = clienteRepository.findById(
        idCliente
    ).orElseThrow {
        EntityNotFoundException("Entity not found $idCliente")
    }!!.let { clienteMapper.from(it) }

    private fun atualizarSaldoCliente(cliente: Cliente, valorTransacao: Long): Cliente {
        val novoSaldo = cliente.saldoInicial!! + valorTransacao
        return cliente.copy(saldoInicial = novoSaldo)
    }

    private fun criarNovaTransacao(transacao: Transacao): TransacaoEntity {
        return TransacaoEntity(
            valor = transacao.valor,
            tipo = transacao.tipo,
            descricao = transacao.descricao
        )
    }

    private fun salvarCliente(cliente: Cliente) {
        clienteRepository.save(
            clienteMapper.from(cliente)
        )
    }

    private fun salvarTransacao(transacaoEntity: TransacaoEntity) {
        transacaoRepository.save(transacaoEntity)
    }
}