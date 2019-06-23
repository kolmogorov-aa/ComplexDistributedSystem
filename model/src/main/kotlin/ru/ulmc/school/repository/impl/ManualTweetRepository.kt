package ru.ulmc.school.repository.impl

import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.ulmc.school.model.TweetModel
import ru.ulmc.school.repository.TweetRepository

@Repository
open class ManualTweetRepository(
        private val sessionFactory: SessionFactory
) : TweetRepository {
    @Transactional
    override fun findTweetsByMessageContent(like: String): List<TweetModel> {
        val query = sessionFactory.currentSession.createQuery(
                "SELECT t FROM TweetModel t WHERE t.message LIKE :msgLike"
        )
        query.setParameter("msgLike", "%$like%")

        return query.resultList as List<TweetModel>
    }
}