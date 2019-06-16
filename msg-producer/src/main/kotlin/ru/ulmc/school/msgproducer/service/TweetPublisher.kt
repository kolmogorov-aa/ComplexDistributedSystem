package ru.ulmc.school.msgproducer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component
import ru.ulmc.school.api.entity.TweetMsg


@Component
class TweetPublisher @Autowired constructor(private val jmsTemplate: JmsTemplate) {

    @Value("\${jms.queue.destination}")
    lateinit var destinationQueue: String

    fun send(tweet: TweetMsg) {
        jmsTemplate.convertAndSend(destinationQueue, tweet)
    }

}