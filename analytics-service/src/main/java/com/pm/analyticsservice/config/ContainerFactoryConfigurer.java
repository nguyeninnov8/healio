package com.pm.analyticsservice.config;
import org.springframework.stereotype.Component;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Component
public class ContainerFactoryConfigurer {

    ContainerFactoryConfigurer(ConcurrentKafkaListenerContainerFactory<?, ?> factory) {
        factory.getContainerProperties().setMissingTopicsFatal(false);
    }
}
