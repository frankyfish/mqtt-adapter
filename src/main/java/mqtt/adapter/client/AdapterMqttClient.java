package mqtt.adapter.client;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface AdapterMqttClient {
    boolean isConnected();
    void connect() throws MqttException;
    void subscribe(String topic, int qos) throws MqttException;
}
