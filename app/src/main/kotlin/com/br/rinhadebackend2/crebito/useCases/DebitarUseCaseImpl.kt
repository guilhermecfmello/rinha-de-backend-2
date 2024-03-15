package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
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
import reactor.core.publisher.Mono

@Component
class DebitarUseCaseImpl(
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository,
    private val dateTimeProvider: DateTimeProvider
) : DebitarUseCase {

    private val clienteMapper = ClienteMapper

    override fun execute(idCliente: Int, transacao: Transacao): Mono<Cliente> {
        return buscaClientePorId(idCliente)
            .flatMap { cliente ->
                validarValorTransacao(cliente, transacao)
                val clienteComSaldoAtualizado = calcularNovoSaldoCliente(cliente, transacao)
                salvarCliente(clienteComSaldoAtualizado)
                    .then(salvarTransacao(transacao, clienteComSaldoAtualizado))
                    .thenReturn(clienteComSaldoAtualizado)
            }
    }

    private fun buscaClientePorId(idCliente: Int): Mono<Cliente> {
        return Mono.fromSupplier {
            clienteRepository.findById(idCliente)
                .orElseThrow { EntityNotFoundException("Entity not found $idCliente") }
                .let { clienteMapper.from(it) }
        }
    }

    private fun validarValorTransacao(cliente: Cliente, transacao: Transacao) {
        if (transacao.valor > (cliente.saldo!! + cliente.limite!!)) {
            throw LimiteNaoDisponivelException(transacao.valor, cliente.id)
        }
    }

    private fun salvarCliente(cliente: Cliente): Mono<Void> {
        return Mono.fromRunnable {
            clienteRepository.save(clienteMapper.from(cliente))
        }
    }

    private fun salvarTransacao(transacao: Transacao, cliente: Cliente): Mono<Void> {
        return Mono.fromRunnable {
            transacaoRepository.save(
                TransacaoEntity(
                    valor = transacao.valor,
                    tipo = transacao.tipo,
                    descricao = transacao.descricao,
                    cliente = clienteMapper.from(cliente),
                    realizadaEm = dateTimeProvider.instante()
                )
            )
        }
    }

    private fun calcularNovoSaldoCliente(cliente: Cliente, transacao: Transacao): Cliente {
        return cliente.copy(saldo = transacao.valor)
    }
}