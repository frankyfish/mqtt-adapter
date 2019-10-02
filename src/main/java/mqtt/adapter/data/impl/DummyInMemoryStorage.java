package mqtt.adapter.data.impl;

import mqtt.adapter.data.AdapterStorage;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class DummyInMemoryStorage implements AdapterStorage {

    private final Map<String, String> receivedMessages = new HashMap<>();

    public void store(String topicName, String data) {
        receivedMessages.put(topicName, data);
    }

    public String getLastMessage(String topicName) {
        return this.receivedMessages.get(topicName);
    }
}
