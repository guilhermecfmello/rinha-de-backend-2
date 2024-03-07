package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.DebitarUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.exceptions.LimiteNaoDisponivelException
import com.br.rinhadebackend2.crebito.models.ClienteEntity
import com.br.rinhadebackend2.crebito.models.TransacaoEntity
import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import com.br.rinhadebackend2.crebito.models.TransacaoResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component

@Component
class DebitarUseCaseImpl(
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository
) : DebitarUseCase {

    override fun execute(idCliente: Int, transacaoRequest: TransacaoRequest): TransacaoResponse {
        val cliente = clienteRepository.findById(idCliente).orElseThrow {
            EntityNotFoundException("Entity not found $idCliente")
        }

        validarValorTransacao(cliente, transacaoRequest)
        val clienteComSaldoAtualizado = calcularNovoSaldoCliente(cliente, transacaoRequest)

        val novaTransacaoEntity = criarNovaTransacao(transacaoRequest)

        clienteRepository.save(clienteComSaldoAtualizado)
        transacaoRepository.save(novaTransacaoEntity)

        return TransacaoResponse(
            clienteComSaldoAtualizado.limite!!,
            clienteComSaldoAtualizado.saldoInicial!!
        )
    }

    private fun validarValorTransacao(cliente: ClienteEntity, transacaoRequest: TransacaoRequest) {
        if(transacaoRequest.valor > (cliente.saldoInicial!! + cliente.limite!!)) {
            throw LimiteNaoDisponivelException(transacaoRequest.valor, cliente.id)
        }
    }

    private fun calcularNovoSaldoCliente(cliente: ClienteEntity, transacaoRequest: TransacaoRequest): ClienteEntity {
        return cliente.copy(saldoInicial = transacaoRequest.valor)
    }

    private fun criarNovaTransacao(transacaoRequest: TransacaoRequest): TransacaoEntity {
        return TransacaoEntity(
            valor = transacaoRequest.valor,
            tipo = transacaoRequest.tipo,
            descricao = transacaoRequest.descricao
        )
    }
}