package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.CreditarUseCase
import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.models.TransacaoEntity
import com.br.rinhadebackend2.crebito.models.Cliente
import com.br.rinhadebackend2.crebito.models.Transacao
import com.br.rinhadebackend2.crebito.useCases.mappers.ClienteMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CreditarUseCaseImpl(
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository,
    private val dateTimeProvider: DateTimeProvider
) : CreditarUseCase {

    private val clienteMapper = ClienteMapper

    override fun execute(idCliente: Int, transacao: Transacao): Mono<Cliente> {
        return buscaClientePorId(idCliente)
            .map { cliente ->
                val clienteComSaldoAtualizado = atualizarSaldoCliente(cliente, transacao.valor)
                val novaTransacaoEntity = criarNovaTransacao(transacao, clienteComSaldoAtualizado)

                salvarCliente(clienteComSaldoAtualizado)
                salvarTransacao(novaTransacaoEntity)

                clienteComSaldoAtualizado
            }
    }

    private fun buscaClientePorId(idCliente: Int): Mono<Cliente> {
        return Mono.fromSupplier {
            clienteRepository.findById(idCliente)
                .orElseThrow { EntityNotFoundException("Entity not found $idCliente") }
                .let { clienteMapper.from(it) }
        }
    }

    private fun atualizarSaldoCliente(cliente: Cliente, valorTransacao: Long): Cliente {
        val novoSaldo = cliente.saldo!! + valorTransacao
        return cliente.copy(saldo = novoSaldo)
    }

    private fun criarNovaTransacao(transacao: Transacao, cliente: Cliente): TransacaoEntity {
        return TransacaoEntity(
            valor = transacao.valor,
            tipo = transacao.tipo,
            descricao = transacao.descricao,
            cliente = clienteMapper.from(cliente),
            realizadaEm = dateTimeProvider.instante()
        )
    }

    private fun salvarCliente(cliente: Cliente): Mono<Void> {
        return Mono.fromRunnable {
            clienteRepository.save(clienteMapper.from(cliente))
        }
    }

    private fun salvarTransacao(transacaoEntity: TransacaoEntity): Mono<Void> {
        return Mono.fromRunnable {
            transacaoRepository.save(transacaoEntity)
        }
    }
}