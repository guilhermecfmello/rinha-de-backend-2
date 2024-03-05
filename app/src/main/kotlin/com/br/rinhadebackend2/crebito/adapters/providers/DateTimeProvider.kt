package com.br.rinhadebackend2.crebito.adapters.providers

import com.br.rinhadebackend2.crebito.adapters.DateTimeProvider
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DateTimeProviderImpl: DateTimeProvider {
    override fun instante(): LocalDateTime {
        return LocalDateTime.now()
    }
}