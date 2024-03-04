package com.br.rinhadebackend2.crebito.adapters.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AppStatusController {


    @GetMapping("/howIsIt")
    fun howIsIt() = ResponseEntity.ok("Everything is ok...")
}