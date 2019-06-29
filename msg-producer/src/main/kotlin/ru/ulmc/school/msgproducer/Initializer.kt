package ru.ulmc.school.msgproducer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import ru.ulmc.school.msgproducer.service.TrumpsTweetProcessor


@Component
class ApplicationStartup : ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    lateinit var processor: TrumpsTweetProcessor

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        processor.start()
    }

} // class