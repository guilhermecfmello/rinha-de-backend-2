package com.br.rinhadebackend2.crebito.adapters.configurations

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false).registerModules(KotlinModule.Builder().build())
}