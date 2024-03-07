package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.DebitarUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.models.TransacaoEntity
import com.br.rinhadebackend2.crebito.exceptions.LimiteNaoDisponivelException
import com.br.rinhadebackend2.crebito.models.Cliente
import com.br.rinhadebackend2.crebito.models.Transacao
import com.br.rinhadebackend2.crebito.useCases.mappers.ClienteMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component

@Component
class DebitarUseCaseImpl(
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository
) : DebitarUseCase {

    private val clienteMapper = ClienteMapper

    override fun execute(idCliente: Int, transacao: Transacao): Cliente {
        val cliente = buscaClientePorId(idCliente)

        validarValorTransacao(cliente, transacao)
        val clienteComSaldoAtualizado = calcularNovoSaldoCliente(cliente, transacao)

        salvarCliente(clienteComSaldoAtualizado)
        salvarTransacao(transacao)

        return clienteComSaldoAtualizado
    }

    private fun buscaClientePorId(idCliente: Int) = clienteRepository.findById(
        idCliente
    ).orElseThrow {
        EntityNotFoundException("Entity not found $idCliente")
    }!!.let { clienteMapper.from(it) }

    private fun validarValorTransacao(cliente: Cliente, transacao: Transacao) {
        if(transacao.valor > (cliente.saldoInicial!! + cliente.limite!!)) {
            throw LimiteNaoDisponivelException(transacao.valor, cliente.id)
        }
    }

    private fun salvarCliente(cliente: Cliente) {
        clienteRepository.save(
            clienteMapper.from(cliente)
        )
    }

    private fun salvarTransacao(transacao: Transacao) {
        transacaoRepository.save(TransacaoEntity(
                valor = transacao.valor,
                tipo = transacao.tipo,
                descricao = transacao.descricao
            )
        )
    }

    private fun calcularNovoSaldoCliente(cliente: Cliente, transacao: Transacao): Cliente {
        return cliente.copy(saldoInicial = transacao.valor)
    }
}