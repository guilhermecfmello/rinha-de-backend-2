package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
import com.br.rinhadebackend2.crebito.adapters.RecuperarExtratoUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.models.ClienteEntity
import com.br.rinhadebackend2.crebito.adapters.repositories.models.TransacaoEntity
import com.br.rinhadebackend2.crebito.models.Extrato
import com.br.rinhadebackend2.crebito.models.Saldo
import com.br.rinhadebackend2.crebito.models.Transacao
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class RecuperarExtratoUseCaseImpl(
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository,
    private val dateTimeProvider: DateTimeProvider
) : RecuperarExtratoUseCase {

    override fun execute(idCliente: Int): Mono<Extrato> {
        return buscaClientePorId(idCliente)
            .flatMap { cliente ->
                findLast10TransactionsByClienteId(idCliente)
                    .map { transacoes ->
                        Extrato(
                            saldo = Saldo(
                                total = cliente.saldo!!,
                                limite = cliente.limite!!,
                                dataExtrato = dateTimeProvider.instante()
                            ),
                            ultimasTransacoes = transacoes.map { transacao ->
                                Transacao(
                                    valor = transacao.valor,
                                    tipo = transacao.tipo,
                                    descricao = transacao.descricao,
                                    realizadaEm = transacao.realizadaEm,
                                )
                            }
                        )
                    }
            }
    }

    private fun buscaClientePorId(idCliente: Int): Mono<ClienteEntity> {
        return Mono.just(
            clienteRepository.findById(idCliente)
                .orElseThrow { EntityNotFoundException("Cliente nao encontrado $idCliente") }
        )
    }

    private fun findLast10TransactionsByClienteId(idCliente: Int): Mono<List<TransacaoEntity>> {
        return Mono.fromSupplier {
            transacaoRepository.findLast10TransactionsByClienteId(idCliente)
        }
    }
}