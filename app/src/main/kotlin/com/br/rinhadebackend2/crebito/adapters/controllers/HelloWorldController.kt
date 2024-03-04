package com.br.rinhadebackend2.crebito.adapters.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {


    @GetMapping("/hello-world")
    fun helloWorld(
        @RequestParam
        name: String = "Anonymous"
    ): ResponseEntity<String>{
        return ResponseEntity.ok("Hello $name")
    }
}