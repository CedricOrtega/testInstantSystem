package fr.instantsystem.testapi.processor.poitiers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.instantsystem.testapi.connector.GrandPoitiersClient;
import fr.instantsystem.testapi.dto.ParkResponseDTO;
import fr.instantsystem.testapi.dto.poitiers.ParkAvailableParkingsDTO;
import fr.instantsystem.testapi.dto.poitiers.RecordsDTO;
import fr.instantsystem.testapi.helper.ParkHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Park processor to manage data.grandpoitiers.fr API
 *
 * @author Cédric Ortega
 *
 */
@Slf4j
@Service
public class ParkProcessor {
    @Autowired
    private GrandPoitiersClient grandPoitiersClient;

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param urlAPIGetParkings url to get list of parkings in Poitiers
     * @param urlAPIGetAvailableParkings url to get list of parking spot available in Poitiers
     * @param latitude latitude the latitude of the city where the user is located - in WGS 84
     * @param longitude longitude the longitude of the city where the user is located - in WGS 84
     * @return park available with their parking spot remaining in Poitiers
     */
    public List<ParkResponseDTO> getParksAvailablePoitiers(URI urlAPIGetParkings, URI urlAPIGetAvailableParkings, Double latitude, Double longitude) {
        Object responseAvailableParkings = grandPoitiersClient.genericGetParkings(urlAPIGetAvailableParkings);
        log.info("API called : " + urlAPIGetAvailableParkings);
        ParkAvailableParkingsDTO parkPresentInProximity = objectMapper.convertValue(responseAvailableParkings, new TypeReference<>() { });
        log.info("Response API : " + parkPresentInProximity);
        Object responseParkings = grandPoitiersClient.genericGetParkings(urlAPIGetParkings);
        log.info("API called : " + urlAPIGetParkings);
        ParkAvailableParkingsDTO parkAvailableParkingsDTO = objectMapper.convertValue(responseParkings, new TypeReference<>() { });
        log.info("Response API : " + parkAvailableParkingsDTO);

        List<ParkResponseDTO> responseDTOList = new ArrayList<>();

        for (RecordsDTO aRecord : parkAvailableParkingsDTO.getRecords()) {
            ParkResponseDTO responseDTO = new ParkResponseDTO();
            for (RecordsDTO recordAvailable : parkPresentInProximity.getRecords()) {
                responseDTO.setName(aRecord.getFields().getNom());
                responseDTO.setCapacity(aRecord.getFields().getNb_places());
                responseDTO.setDistanceInMeters(ParkHelper.haversineFormula(aRecord.getFields().getGeo_point_2d().get(0), latitude ,aRecord.getFields().getGeo_point_2d().get(1), longitude));
                if(aRecord.getFields().getNom().equals(recordAvailable.getFields().getNom())){
                    responseDTO.setParkingSpotsAvailable(recordAvailable.getFields().getPlaces_restantes());
                    responseDTO.setCapacity(recordAvailable.getFields().getCapacite());
                    break;
                }
            }
            responseDTOList.add(responseDTO);
        }

        return ParkHelper.sortedByDistance(responseDTOList);
    }
}
