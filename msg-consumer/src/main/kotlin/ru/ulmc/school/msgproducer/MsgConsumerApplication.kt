package ru.ulmc.school.msgproducer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jms.annotation.EnableJms



@EnableJms
@SpringBootApplication
class MsgConsumerApplication

fun main(args: Array<String>) {
	val context = runApplication<MsgConsumerApplication>(*args)
}
