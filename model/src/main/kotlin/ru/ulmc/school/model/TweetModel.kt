package ru.ulmc.school.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_TWEETS")
data class TweetModel(

        @field:Id
        @field:Column(name = "id")
        val id: String = UUID.randomUUID().toString(),

        @field:Column(name = "date_time")
        val dateTime: LocalDateTime,

        @field:Column(name = "message")
        val message: String
)