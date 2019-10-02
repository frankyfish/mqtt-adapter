package mqtt.adapter.client;

import io.micronaut.discovery.event.ServiceStartedEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import lombok.extern.slf4j.Slf4j;
import mqtt.adapter.config.MqttConnectionProperties;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class PahoAdapterMqttClient implements AdapterMqttClient {

    public static final String MQTT_TCP_PREFIX = "tcp://";
    private final String mqttHost;
    private final String mqttPort;
    private final String mqttClientId;
    private MqttClient client;
    private MqttCallback mqttCallback;

    @Inject
    public PahoAdapterMqttClient(MqttConnectionProperties connectionProperties, MqttCallback mqttCallback) {
        this.mqttHost = connectionProperties.getMqttBrokerAddress();
        this.mqttPort = connectionProperties.getMqttBrokerPort();
        this.mqttClientId = connectionProperties.getClientId();
        this.mqttCallback = mqttCallback;
    }

    @Override
    public boolean isConnected() {
        return this.client.isConnected();
    }

    @Override
    public void connect() throws MqttException {
        String mqttBrokerAddress = MQTT_TCP_PREFIX + mqttHost + ":" + mqttPort;
        log.debug("Trying to establish connection with MQTT Broker with address = {}", mqttBrokerAddress);
        org.eclipse.paho.client.mqttv3.MqttClient mqttClient =
                new org.eclipse.paho.client.mqttv3.MqttClient(mqttBrokerAddress, mqttClientId);
        mqttClient.connect();
        log.debug("Connected = {} to MQTT Broker with address= {}.", mqttClient.isConnected(), mqttBrokerAddress);
        this.client = mqttClient;
        this.client.setCallback(mqttCallback);
    }

//    @EventListener
//    private void setCallbackAfterStart(final ServiceStartedEvent serviceStartedEvent) {
//        // todo:
//        this.client.setCallback(mqttCallback);
//    }

    @Override
    public void subscribe(String topic, int qos) throws MqttException {
        log.debug("Subscribing on topic {} with QoS = {}", topic, qos);
        this.client.subscribe(topic, qos);
    }
}
