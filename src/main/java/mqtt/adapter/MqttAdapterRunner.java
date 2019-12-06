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

/**
 * This class works as a main entry-point for the program.
 * Key method is {@link #run(ServiceStartedEvent)}
 */

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

    /**
     * This method works as an entry-point by listening for Micronaut's {@link ServiceStartedEvent}
     * After event is caught attempt to establish MQTT connection to broker is made.
     *
     * @param serviceStartedEvent event produced by Micronaut on service start-up
     * @throws MqttException in case of failed attempt to connect
     */
    @EventListener
    public void run(final ServiceStartedEvent serviceStartedEvent) throws MqttException {
        mqttClient.connect();
        if (mqttClient.isConnected()) {
            subscribeOnTopics();
        }
    }

    private void subscribeOnTopics() throws MqttException {
        if (mqttTopics != null
                && !mqttTopics.isEmpty()) {
            for (String topic : mqttTopics) {
                mqttClient.subscribe(topic, 0); // todo: make qos configurable (Map?)
            }
        }
    }

}
