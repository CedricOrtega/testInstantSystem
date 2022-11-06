package fr.instantsystem.testapi.service;

import fr.instantsystem.testapi.constant.ConstantOpenAPIEndpoint;
import fr.instantsystem.testapi.dto.ParkResponseDTO;
import fr.instantsystem.testapi.enumeration.CityEnum;
import fr.instantsystem.testapi.exception.ParkNotFoundException;
import fr.instantsystem.testapi.processor.poitiers.ParkProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * Park service to control city and coordinates received in request and to send list of park available
 *
 * @author Cédric Ortega
 *
 */
@Service
public class ParkService {
    @Autowired
    private ParkProcessor parkProcessor;

    /**
     *
     * @param city the name of the city where the user is located
     * @param latitude the latitude of the city where the user is located - in WGS 84
     * @param longitude the longitude of the city where the user is located - in WGS 84
     * @return park available in Poitiers
     * @throws ParkNotFoundException when no API is detected with the city sent in request
     */
    public List<ParkResponseDTO> getParksAvailable(String city, Double latitude, Double longitude) throws ParkNotFoundException {
        List<ParkResponseDTO> response = manageCityApi(city, latitude, longitude);
        if(response.isEmpty()){
            throw new ParkNotFoundException("City Not Found in our API.");
        }
        return manageCityApi(city, latitude, longitude);
    }

    /**
     *
     * @param city the name of the city where the user is located
     * @param latitude the latitude of the city where the user is located - in WGS 84
     * @param longitude the longitude of the city where the user is located - in WGS 84
     * @return park available in proximity depending the city
     */
    public List<ParkResponseDTO> manageCityApi(String city, Double latitude, Double longitude) {
        if(CityEnum.POITIERS.name().equalsIgnoreCase(city)){
            return parkProcessor.getParksAvailablePoitiers(URI.create(ConstantOpenAPIEndpoint.DATA_GRAND_POITIERS_LIST_PARKINGS),
                                             URI.create(ConstantOpenAPIEndpoint.DATA_GRAND_POITIERS_AVAILABLE_PARKINGS),
                                             latitude, longitude);
        }
        else{
            return Collections.emptyList();
        }
    }
}
