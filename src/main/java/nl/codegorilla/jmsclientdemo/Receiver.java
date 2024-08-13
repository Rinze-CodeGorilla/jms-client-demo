package nl.codegorilla.jmsclientdemo;

import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Map;

enum Status {
    SUCCESS;
}

@Component
public class Receiver {
    @JmsListener(destination = "pub-sub-announcements")
    void processAnnouncement(Map<String, String> announcementMap) {
        var announcement = new Announcement(announcementMap.get("name"), announcementMap.get("message"));
        System.out.println("Received <%s>".formatted(announcement));
    }

    @JmsListener(destination = "pub-sub-announcements")
    @SendTo("status")
    Status prettyAnnouncement(Map<String, String> announcementMap) {
        var announcement = new Announcement(announcementMap.get("name"), announcementMap.get("message"));
        LoggerFactory.getLogger("pretty").info(announcement.toString());
        return Status.SUCCESS;
    }
}

@Configuration
@EnableJms
class ReceiverConfiguration {

// needs a custom factory to be able to set up a client id
    @Bean
    DefaultJmsListenerContainerFactory jmsListenerContainerFactory(SingleConnectionFactory connectionFactory,
                                                                   DefaultJmsListenerContainerFactoryConfigurer configurer, String clientId) {
        connectionFactory.setClientId(clientId);
        var factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
