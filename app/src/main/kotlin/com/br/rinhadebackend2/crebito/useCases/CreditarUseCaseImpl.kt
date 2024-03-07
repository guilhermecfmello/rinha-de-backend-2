package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.CreditarUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.models.ClienteEntity
import com.br.rinhadebackend2.crebito.models.TransacaoEntity
import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import com.br.rinhadebackend2.crebito.models.TransacaoResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component

@Component
class CreditarUseCaseImpl(
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository
) : CreditarUseCase {

    override fun execute(idCliente: Int, transacaoRequest: TransacaoRequest): TransacaoResponse {
        val cliente = clienteRepository.findById(idCliente).orElseThrow {
            EntityNotFoundException("Entity not found $idCliente")
        }
        val clienteComSaldoAtualizado = atualizarSaldoCliente(cliente, transacaoRequest.valor)
        val novaTransacaoEntity = criarNovaTransacao(transacaoRequest)

        salvarCliente(clienteComSaldoAtualizado)
        salvarTransacao(novaTransacaoEntity)

        return TransacaoResponse(
            limite = clienteComSaldoAtualizado.limite!!,
            saldo = clienteComSaldoAtualizado.saldoInicial!!
        )
    }
    private fun atualizarSaldoCliente(cliente: ClienteEntity, valorTransacao: Long): ClienteEntity {
        val novoSaldo = cliente.saldoInicial!! + valorTransacao
        return cliente.copy(saldoInicial = novoSaldo)
    }

    private fun criarNovaTransacao(transacaoRequest: TransacaoRequest): TransacaoEntity {
        return TransacaoEntity(
            valor = transacaoRequest.valor,
            tipo = transacaoRequest.tipo,
            descricao = transacaoRequest.descricao
        )
    }

    private fun salvarCliente(cliente: ClienteEntity) {
        clienteRepository.save(cliente)
    }

    private fun salvarTransacao(transacaoEntity: TransacaoEntity) {
        transacaoRepository.save(transacaoEntity)
    }
}