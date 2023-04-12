package com.example.demo.usecase

import com.example.demo.entities.User
import com.example.demo.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun findAll(): Iterable<User> = userRepository.findAll()

    fun findByLogin(login: String) = userRepository.findByLogin(login)
}
