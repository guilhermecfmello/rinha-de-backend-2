package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.RecuperarExtratoUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.models.Extrato
import com.br.rinhadebackend2.crebito.models.Saldo
import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Component

@Component
class RecuperarExtratoUseCaseImpl(
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository,
    private val dateTimeProvider: DateTimeProvider
) : RecuperarExtratoUseCase {

    override fun execute(idCliente: Int): Extrato {

        val cliente = clienteRepository.findById(idCliente).orElseThrow{
            EntityNotFoundException("Cliente nao encontrado $idCliente")
        }

        val transacoes = transacaoRepository.findLast10TransactionsByClienteId(
            idCliente
        )

        return Extrato(
            saldo = Saldo(
                total = cliente.saldoInicial!!,
                limite = cliente.limite!!,
                dataExtrato = dateTimeProvider.instante()
            ),
            ultimasTransacoes = transacoes
        )

    }
}