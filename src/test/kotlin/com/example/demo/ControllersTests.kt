package com.example.demo

import RenderedArticle
import com.example.demo.entities.User
import com.example.demo.usecase.ArticleService
import com.example.demo.usecase.UserService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest
class ControllersTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var articleService: ArticleService

    @MockkBean
    lateinit var userService: UserService

    @Test
    fun `List articles`() {
        val johnDoe = User("johnDoe", "John", "Doe")
        val loremArticle = RenderedArticle("lorem", "Lorem", "Lorem", "dolor sit amet", johnDoe, "2023-01-01")
        val ipsumArticle = RenderedArticle("ipsum", "Ipsum", "Ipsum", "dolor sit amet", johnDoe, "2023-01-01")

        every { articleService.findAllByOrderByAddedAtDesc() } returns listOf(loremArticle, ipsumArticle)

        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].author.login").value(johnDoe.login))
            .andExpect(jsonPath("\$.[0].slug").value(loremArticle.slug))
            .andExpect(jsonPath("\$.[1].author.login").value(johnDoe.login))
            .andExpect(jsonPath("\$.[1].slug").value(ipsumArticle.slug))
    }

    @Test
    fun `List users`() {
        val johnDoe = User("johnDoe", "John", "Doe")
        val janeDoe = User("janeDoe", "Jane", "Doe")

        every { userService.findAll() } returns listOf(johnDoe, janeDoe)

        mockMvc.perform(get("/api/user/").accept(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$.[0].login").value(johnDoe.login))
            .andExpect(jsonPath("\$.[1].login").value(janeDoe.login))
    }
}
