package ru.ulmc.school.events

import org.springframework.context.ApplicationEvent
import ru.ulmc.school.api.entity.TweetMsg

data class TweetEvent(
        private val eventSource: Any,
        val tweet: TweetMsg
) : ApplicationEvent(eventSource)
