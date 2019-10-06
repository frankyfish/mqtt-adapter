package mqtt.adapter.service.impl;

import lombok.extern.slf4j.Slf4j;
import mqtt.adapter.data.AdapterStorage;
import mqtt.adapter.service.MetricsStorageService;
import pi.tracker.dto.PiSensorHubMetric;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class MetricsStorageServiceImpl implements MetricsStorageService {

    private final AdapterStorage adapterStorage;

    @Inject
    public MetricsStorageServiceImpl(AdapterStorage storage) {
        this.adapterStorage = storage;
    }

    @Override
    public PiSensorHubMetric find(String key) {
        if (adapterStorage.contains(key)) {
            log.debug("Found messages for topic = {}", key);
            return (PiSensorHubMetric) adapterStorage.getLastMessage(key);
        }
        log.debug("There were no messages received on topic = {}", key);
        return new PiSensorHubMetric();
    }
}
