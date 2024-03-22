package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
import com.br.rinhadebackend2.crebito.adapters.DebitarUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.exceptions.LimiteNaoDisponivelException
import com.br.rinhadebackend2.crebito.models.Cliente
import com.br.rinhadebackend2.crebito.models.Transacao
import com.br.rinhadebackend2.crebito.useCases.mappers.ClienteMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.dao.DataAccessResourceFailureException
import org.springframework.data.repository.findByIdOrNull
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
        return Mono.fromSupplier{
            val cliente = clienteRepository.findByIdOrNull(
                idCliente
            ) ?: throw(EntityNotFoundException("Cliente nao encontrado"))
            try {
                val novoSaldo = clienteRepository.debitarValorNoCliente(
                    cliente.id,
                    transacao.valor,
                    transacao.descricao,
                    dateTimeProvider.instante(),
                    1
                )
                clienteMapper.from(
                    cliente.copy(
                        saldo = novoSaldo.toLong()
                    )
                )
            } catch (exception: Exception){
                when(exception){
                    is DataAccessResourceFailureException -> throw LimiteNaoDisponivelException(transacao.valor, cliente.id)
                    else -> throw exception
                }
            }
        }
    }
}