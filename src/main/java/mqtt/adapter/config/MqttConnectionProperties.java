package mqtt.adapter.config;

import io.micronaut.context.annotation.Value;
import lombok.Getter;

import javax.inject.Singleton;

@Singleton
@Getter
public class MqttConnectionProperties {

    @Value("${mqtt-adapter.broker.address}")
    private String mqttBrokerAddress;

    @Value("${mqtt-adapter.broker.port}")
    private String mqttBrokerPort;

    @Value("${mqtt-adapter.client-id}")
    private String clientId;

}
