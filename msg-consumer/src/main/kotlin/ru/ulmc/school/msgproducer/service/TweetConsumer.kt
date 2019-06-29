package ru.ulmc.school.msgproducer.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import ru.ulmc.school.api.entity.TweetMsg


@Component
class TweetConsumer  {
    private val log: Logger = LoggerFactory.getLogger(TweetConsumer::class.java)

    @JmsListener(destination = "\${jms.queue.destination}")
    fun send(tweet: TweetMsg) {
       log.info("Received {} {}", tweet)
    }

}