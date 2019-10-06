package mqtt.adapter.service;

import pi.tracker.dto.PiSensorHubMetric;

public interface MetricsStorageService {

    PiSensorHubMetric find(String key);

}
