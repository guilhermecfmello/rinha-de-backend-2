package com.br.rinhadebackend2.crebito.adapters

import java.time.LocalDateTime

interface DateTimeProvider {

    fun instante(): LocalDateTime
}