package com.example.demo.usecase

import RenderedArticle
import com.example.demo.entities.Article
import com.example.demo.repositories.ArticleRepository
import com.example.demo.utils.format
import org.springframework.stereotype.Service

@Service
class ArticleService(private val articleRepository: ArticleRepository) {

    fun findAllByOrderByAddedAtDesc() = articleRepository.findAllByOrderByAddedAtDesc().map { it.render() }

    fun findBySlug(slug: String) = articleRepository.findBySlug(slug)?.render()

    private fun Article.render() = RenderedArticle(
        slug,
        title,
        headline,
        content,
        author,
        addedAt.format(),
    )
}
