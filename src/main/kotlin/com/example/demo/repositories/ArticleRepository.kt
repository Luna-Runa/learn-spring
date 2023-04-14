package com.example.demo.repositories

import com.example.demo.entities.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findBySlug(slug: String): Article?
    fun findAllByOrderByAddedAtDesc(): List<Article>
    fun findByContentOrSlug(content: String, slug: String): Article?
}
