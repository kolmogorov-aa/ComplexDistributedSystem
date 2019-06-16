package ru.ulmc.school.msgproducer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.core.JmsTemplate



@EnableJms
@SpringBootApplication
class MsgConsumerApplication

fun main(args: Array<String>) {
	val context = runApplication<MsgConsumerApplication>(*args)
	val jmsTemplate = context.getBean(JmsTemplate::class.java)
	jmsTemplate.convertAndSend("robotCommand", "test")

}
