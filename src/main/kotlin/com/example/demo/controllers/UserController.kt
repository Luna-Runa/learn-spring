package com.example.demo.controllers

import com.example.demo.usecase.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @GetMapping()
    fun findAll() = userService.findAll()

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) = userService.findByLogin(login)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist")
}
