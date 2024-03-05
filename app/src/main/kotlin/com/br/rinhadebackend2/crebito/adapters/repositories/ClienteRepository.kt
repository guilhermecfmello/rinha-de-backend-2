package com.br.rinhadebackend2.crebito.adapters.repositories

import com.br.rinhadebackend2.crebito.models.ClienteEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteRepository: JpaRepository<ClienteEntity, Int>{

}