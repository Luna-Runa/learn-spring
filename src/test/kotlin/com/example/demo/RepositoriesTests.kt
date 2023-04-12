package com.example.demo

import com.example.demo.entities.Article
import com.example.demo.entities.User
import com.example.demo.repositories.ArticleRepository
import com.example.demo.repositories.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository,
) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        // given
        val johnDoe = User("johnDoe", "John", "Doe")
        entityManager.persist(johnDoe)
        val article = Article("Lorem", "Lorem", "dolor sit amet", johnDoe)
        entityManager.persist(article)
        entityManager.flush()

        // when
        val found = articleRepository.findByIdOrNull(article.id!!)

        // then
        assertThat(found).isEqualTo(article)
    }

    @Test
    fun `When findByLogin then return User`() {
        // given
        val johnDoe = User("johnDoe", "John", "Doe")
        entityManager.persist(johnDoe)
        entityManager.flush()

        // when
        val user = userRepository.findByLogin(johnDoe.login)

        // then
        assertThat(user).isEqualTo(johnDoe)
    }

    @Test
    fun `슬러그나 컨텐츠가 일치하면 반환`() {
        // given
        val johnDoe = User("johnDoe", "John", "Doe")
        entityManager.persist(johnDoe)
        val article = Article("Lorem", "Lorem", "dolor sit amet", johnDoe)
        entityManager.persist(article)
        entityManager.flush()

        // when
        val foundByContent = articleRepository.findByContentOrSlug(article.content, "not")
        val foundBySlug = articleRepository.findByContentOrSlug("not", article.slug)
        val notFound = articleRepository.findByContentOrSlug("not", "not")

        // then
        assertThat(foundByContent).isEqualTo(article)
        assertThat(foundBySlug).isEqualTo(article)
        assertThat(notFound).isEqualTo(null)
    }
}
