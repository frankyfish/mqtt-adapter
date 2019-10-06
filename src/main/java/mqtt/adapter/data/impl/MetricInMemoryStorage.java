package mqtt.adapter.data.impl;

import lombok.extern.slf4j.Slf4j;
import mqtt.adapter.data.AdapterStorage;
import pi.tracker.dto.PiSensorHubMetric;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
public class MetricInMemoryStorage implements AdapterStorage {

    private Map<String, PiSensorHubMetric> storage = new HashMap<>(1);

    @Override
    public void store(String topicName, Object data) {

        if (data instanceof PiSensorHubMetric) {
            log.debug("Storing metric from topic = {}", topicName);
            storage.put(topicName, (PiSensorHubMetric) data);
        }
    }
    // todo impementations for these
    @Override
    public PiSensorHubMetric getLastMessage(String topic) {
        return null;
    }

    @Override
    public boolean contains(String topic) {
        return storage.containsKey(topic);
    }
}
