package ru.ulmc.school.repository.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.ulmc.school.model.TweetModel
import ru.ulmc.school.repository.TweetRepository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

//@Repository
@Component
open class ManualTweetRepository : TweetRepository {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Transactional
    override fun findTweetsByMessageContent(like: String): List<TweetModel> {
        val query = entityManager.createQuery(
                "SELECT t FROM TweetModel t WHERE t.message LIKE :msgLike"
        )
        query.setParameter("msgLike", "%$like%")
        @Suppress("unchecked_cast")
        return query.resultList as List<TweetModel>
    }
}