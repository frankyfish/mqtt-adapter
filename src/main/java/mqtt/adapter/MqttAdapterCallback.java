package mqtt.adapter;

import lombok.extern.slf4j.Slf4j;
import mqtt.adapter.data.AdapterStorage;
import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import pi.tracker.dto.PiSensorHubMetric;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class MqttAdapterCallback implements MqttCallback {

    private AdapterStorage storage;

    @Inject
    public MqttAdapterCallback(AdapterStorage storage) {
        this.storage = storage;
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.error("Connection was lost, cause:", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        log.debug("MQTT Message arrived, topic = {}", topic);
        PiSensorHubMetric metric = SerializationUtils.deserialize(message.getPayload());
        log.debug("Received message on topic: {} with payload: {}. Storing...", topic, metric);
        storage.store(topic, metric);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {}
}
