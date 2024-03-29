package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.RecuperarExtratoUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.models.Extrato
import com.br.rinhadebackend2.crebito.models.Saldo
import com.br.rinhadebackend2.crebito.models.Transacao
import jakarta.persistence.EntityNotFoundException
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
                total = cliente.saldo!!,
                limite = cliente.limite!!,
                dataExtrato = dateTimeProvider.instante()
            ),
            ultimasTransacoes = transacoes.map {
                Transacao(
                    valor = it.valor,
                    tipo = it.tipo,
                    descricao = it.descricao,
                    realizadaEm = it.realizadaEm,
                )
            }
        )

    }
}