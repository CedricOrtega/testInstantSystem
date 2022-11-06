package fr.instantsystem.testapi.connector;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(value = "placeholder", url = "https://data.grandpoitiers.fr/api/records/1.0/search//")
public interface GrandPoitiersClient {
    @GetMapping
    Object genericGetParkings(URI baseUrl);
}
