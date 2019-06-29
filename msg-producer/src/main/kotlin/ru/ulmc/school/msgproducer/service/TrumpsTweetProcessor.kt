package ru.ulmc.school.msgproducer.service

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import ru.ulmc.school.api.entity.TweetMsg
import java.lang.Math.abs
import java.lang.Thread.currentThread
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class TrumpsTweetProcessor(private val tweetPublisher: TweetPublisher) {
    private val log: Logger = LoggerFactory.getLogger(TrumpsTweetProcessor::class.java)
    private val msgs: MutableList<TweetMsg> = ArrayList()
    private val running: AtomicBoolean = AtomicBoolean(true)

    @PostConstruct
    fun init(): Unit {
        try {
            val bootstrapSchema = CsvSchema.builder()
                    .addColumn("date", CsvSchema.ColumnType.STRING)
                    .addColumn("text", CsvSchema.ColumnType.STRING)
                    .build()
                    .withColumnSeparator(';')
                    //.withoutQuoteChar()
                    .withoutEscapeChar()
                    .withoutHeader()

            val mapper = CsvMapper()
            val file = ClassPathResource("trumps_tweets.csv").file
            val readValues = mapper.reader(bootstrapSchema).forType(TweetMsg::class.java).readValues<TweetMsg>(file)
            msgs.addAll(readValues.readAll())
        } catch (e: Exception) {
            log.error("Error occurred while loading object list from file trumps_tweets.csv", e)
            throw e
        }
    }

    @PreDestroy
    fun stop() {
        running.set(false)
    }

    @Async
    fun start() {
        while (!currentThread().isInterrupted && running.get()) {
            msgs.forEach {
                log.info("Sending tweet: {}", it)
                tweetPublisher.send(it)
                TimeUnit.MILLISECONDS.sleep(abs(200 + 200 * ThreadLocalRandom.current().nextDouble()).toLong())
            }
        }
    }

}