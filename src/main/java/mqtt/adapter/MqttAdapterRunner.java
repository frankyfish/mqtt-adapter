package mqtt.adapter;

import io.micronaut.context.annotation.Value;
import io.micronaut.discovery.event.ServiceStartedEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import lombok.extern.slf4j.Slf4j;
import mqtt.adapter.client.AdapterMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;

@Slf4j
@Singleton
public class MqttAdapterRunner {

    public AdapterMqttClient mqttClient;

    @Value("${mqtt-adapter.topics}")
    private ArrayList<String> mqttTopics;

    @Inject
    public MqttAdapterRunner(AdapterMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @EventListener
    public void run(final ServiceStartedEvent serviceStartedEvent) {
        try {
            mqttClient.connect();
            if (mqttClient.isConnected()) {
                subscribeOnTopics();
            } else {
                throw new RuntimeException("Failed to establish MQTT Connection");
            }
        } catch (MqttException e) {
            log.error("Failed to establish connection with MQTT Broker", e);
            throw new RuntimeException(e);
        }
    }

    private void subscribeOnTopics() throws MqttException {
        if (mqttTopics != null
                &&!mqttTopics.isEmpty()) {
            for (String topic: mqttTopics) {
                mqttClient.subscribe(topic, 0); // todo: make qos configurable
            }
        }
    }

}
