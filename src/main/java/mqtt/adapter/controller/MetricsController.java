package mqtt.adapter.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import lombok.extern.slf4j.Slf4j;
import mqtt.adapter.service.MetricsStorageService;
import pi.tracker.dto.PiSensorHubMetric;

import javax.inject.Inject;

@Slf4j
@Controller("/metrics")
public class MetricsController {

    private final MetricsStorageService metricsService;

    @Inject
    public MetricsController(MetricsStorageService metricsStorageService) {
        this.metricsService = metricsStorageService;
    }

    @Get(value = "/{topic}", produces = MediaType.APPLICATION_JSON)
    public PiSensorHubMetric getMetric(@PathVariable("topic") String topic) {
        log.debug("Got request for message with topic = {}", topic);
        return metricsService.find(topic);
    }

}
