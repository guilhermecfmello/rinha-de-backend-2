package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.CreditarUseCase
import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.models.Cliente
import com.br.rinhadebackend2.crebito.models.Transacao
import com.br.rinhadebackend2.crebito.useCases.mappers.ClienteMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CreditarUseCaseImpl(
    private val clienteRepository: ClienteRepository,
    private val dateTimeProvider: DateTimeProvider
) : CreditarUseCase {

    private val clienteMapper = ClienteMapper

    override fun execute(idCliente: Int, transacao: Transacao): Mono<Cliente> {
        return Mono.fromSupplier{
            val novaTransacao = transacao.copy(
                realizadaEm = dateTimeProvider.instante()
            )

            val clienteEntity = clienteRepository.findByIdOrNull(
                idCliente
            ) ?: throw(EntityNotFoundException("Cliente nao encontrado"))

            val novoSaldo = clienteRepository.creditarValorNoCliente(
                clienteEntity.id,
                novaTransacao.valor,
                novaTransacao.descricao,
                novaTransacao.realizadaEm!!,
                1
            )
            Cliente(
                clienteEntity.id,
                clienteEntity.nome,
                clienteEntity.limite,
                novoSaldo.toLong()
            )
        }
    }
}