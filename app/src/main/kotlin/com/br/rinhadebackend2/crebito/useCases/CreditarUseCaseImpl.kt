package com.br.rinhadebackend2.crebito.useCases

import com.br.rinhadebackend2.crebito.adapters.CreditarUseCase
import com.br.rinhadebackend2.crebito.adapters.repositories.ClienteRepository
import com.br.rinhadebackend2.crebito.adapters.repositories.TransacaoRepository
import com.br.rinhadebackend2.crebito.models.TransacaoEntity
import com.br.rinhadebackend2.crebito.models.TransacaoRequest
import com.br.rinhadebackend2.crebito.models.TransacaoResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CreditarUseCaseImpl(
    private val transacaoRepository: TransacaoRepository,
    private val clienteRepository: ClienteRepository
) : CreditarUseCase {

    override fun execute(idCliente: Int, transacaoRequest: TransacaoRequest): TransacaoResponse {
        // Get cliente
        return try{
            val cliente = clienteRepository.getReferenceById(idCliente)
            val novoSaldo = cliente.saldoInicial!! + transacaoRequest.valor
            val clienteComSaldoAtualizado = cliente.copy(
                saldoInicial = novoSaldo
            )

            val novaTransacaoEntity = TransacaoEntity(
                valor = transacaoRequest.valor,
                tipo = transacaoRequest.tipo,
                descricao = transacaoRequest.descricao,
            )

            clienteRepository.save(clienteComSaldoAtualizado)
            transacaoRepository.save(novaTransacaoEntity)
            TransacaoResponse(
                limite = clienteComSaldoAtualizado.limite!!,
                saldo = clienteComSaldoAtualizado.saldoInicial!!
            )
        } catch (notFoundException: EntityNotFoundException) {
            // not found
            throw notFoundException
        } catch(e: Exception) {
            // default behavior for other errors
            throw e
        }
    }
}