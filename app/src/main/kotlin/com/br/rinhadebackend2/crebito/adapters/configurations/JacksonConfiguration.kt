package com.br.rinhadebackend2.crebito.adapters.configurations

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.format.DateTimeFormatter


@Configuration
class JacksonConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper()
        .configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false)
        .registerModules(KotlinModule.Builder().build())
        .registerModule(
            JavaTimeModule()
                .addSerializer(LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME)
                )
        )
}