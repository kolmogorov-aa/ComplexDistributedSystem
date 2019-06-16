package ru.ulmc.school.msgproducer

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.test.context.junit4.SpringRunner
import ru.ulmc.school.msgproducer.service.TrumpsTweetProcessor

@RunWith(SpringRunner::class)
@SpringBootTest
class MsgProducerApplicationTests {
	@Autowired
	lateinit var processor: TrumpsTweetProcessor

	@Test
	fun contextLoads() {

	}

}
