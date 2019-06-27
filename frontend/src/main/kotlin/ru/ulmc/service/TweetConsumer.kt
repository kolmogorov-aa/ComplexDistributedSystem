package ru.ulmc.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component
import ru.ulmc.school.api.entity.TweetMsg
import ru.ulmc.events.TweetEvent


@Component
class TweetConsumer(
        private val jmsTemplate: JmsTemplate,
        @Qualifier("tweetsMulticaster")
        private val appMulticaster: ApplicationEventMulticaster
) {
    private val log: Logger = LoggerFactory.getLogger(TweetConsumer::class.java)

    @JmsListener(destination = "\${jms.queue.destination}")
    fun send(tweet: TweetMsg) {
        log.info("Received {} {}", tweet)
        appMulticaster.multicastEvent(TweetEvent(this, tweet))
    }

}