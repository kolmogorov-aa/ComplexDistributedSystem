package ru.ulmc.school.msgproducer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jms.annotation.EnableJms
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyAcceptorFactory
import org.apache.activemq.artemis.api.core.TransportConfiguration
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer
import org.springframework.context.annotation.Configuration


@EnableAsync
@EnableScheduling
@EnableJms
@SpringBootApplication
class MsgProducerApplication

fun main(args: Array<String>) {
	runApplication<MsgProducerApplication>(*args)
}

@SpringBootConfiguration
class ArtemisConfig : ArtemisConfigurationCustomizer {
	override fun customize(configuration: org.apache.activemq.artemis.core.config.Configuration) {
		configuration.addConnectorConfiguration("nettyConnector", TransportConfiguration(NettyConnectorFactory::class.java.name))
		configuration.addAcceptorConfiguration(TransportConfiguration(NettyAcceptorFactory::class.java.name))
	}
}