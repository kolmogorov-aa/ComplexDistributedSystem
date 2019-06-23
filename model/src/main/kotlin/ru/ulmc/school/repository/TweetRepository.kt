package ru.ulmc.school.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.ulmc.school.model.TweetModel

interface TweetRepository {
    fun findTweetsByMessageContent(like: String): List<TweetModel>
}

@Repository
interface CrudTweetRepository : CrudRepository<TweetModel, String>