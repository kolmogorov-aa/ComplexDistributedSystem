package ru.ulmc.school.msgproducer.service

import com.google.common.collect.Lists
import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.HttpHosts
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.event.Event
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.BasicClient
import com.twitter.hbc.httpclient.auth.OAuth1
import org.apache.activemq.artemis.utils.RandomUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import ru.ulmc.school.api.entity.TweetMsg
import java.lang.Thread.currentThread
import java.time.LocalDateTime.now
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

open class TweetProcessor(private val tweetPublisher: TweetPublisher) {
    private val msgQueue: BlockingQueue<String> = LinkedBlockingQueue(100000)
    private val eventQueue: BlockingQueue<Event> = LinkedBlockingQueue<Event>(1000)

    @Value("\${twitter.consumerKey}")
    private var consumerKey = "consumerKey"

    @Value("\${twitter.consumerSecret}")
    private var consumerSecret = "consumerSecret"

    @Value("\${twitter.token}")
    private var token = "token"

    @Value("\${twitter.secret}")
    private var secret = "secret"

    private lateinit var hosebirdClient: BasicClient

    //@PostConstruct
    fun init() {
        val hosebirdHosts = HttpHosts(Constants.STREAM_HOST)
        val hosebirdEndpoint = StatusesFilterEndpoint()
        val followings = Lists.newArrayList(1234L, 566788L)
        val terms = Lists.newArrayList("twitter", "api")
        hosebirdEndpoint.followings(followings)
        hosebirdEndpoint.trackTerms(terms)

        val hosebirdAuth = OAuth1(consumerKey, consumerSecret, token, secret)
        val builder = ClientBuilder()
                .name("Hosebird-Client-01")
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(StringDelimitedProcessor(msgQueue))
                .eventMessageQueue(eventQueue)

        hosebirdClient = builder.build()
    }

   // @Async
    open fun start() {
        //hosebirdClient.connect()
        while (!currentThread().isInterrupted /*!hosebirdClient.isDone*/) {
            // val msg = msgQueue.take()
            //tweetPublisher.send(TweetMsg(now(), RandomUtil.randomString()))
        }
    }

   // @PreDestroy
    fun close() {
        hosebirdClient.stop()
    }
}