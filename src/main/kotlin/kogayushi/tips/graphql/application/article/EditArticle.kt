package kogayushi.tips.graphql.application.article

import kogayushi.tips.graphql.adapter.presentation.graphql.OmittableValue
import kogayushi.tips.graphql.model.article.Article
import kogayushi.tips.graphql.model.article.ArticleRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class EditArticle(
    private val articleRepository: ArticleRepository,
) {
    fun handle(inputData: EditArticleInputData): Article {
        val article = articleRepository.resolveByArticleId(inputData.articleId)
            ?: throw IllegalArgumentException("Article not found: ${inputData.articleId}")

        val updatedArticle = article.updated(
            title = inputData.title,
            content = inputData.content,
            scheduledPublishDate = inputData.scheduledPublishDate
        )

        articleRepository.update(updatedArticle)
        return updatedArticle
    }
}

data class EditArticleInputData(
    val articleId: UUID,
    val title: OmittableValue<String>,
    val content: OmittableValue<String>,
    val scheduledPublishDate: OmittableValue<LocalDateTime?>,
)
