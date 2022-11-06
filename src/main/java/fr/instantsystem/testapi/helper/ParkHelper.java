package fr.instantsystem.testapi.helper;

import fr.instantsystem.testapi.dto.ParkResponseDTO;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ParkHelper is a utility class containing methods that will be used for the different API calls of any city.
 *
 * @author Cédric Ortega
 *
 */
@UtilityClass
public class ParkHelper {

    public static Double haversineFormula(Double lat1, Double lat2, Double lon1, Double lon2) {
        Double earthRadius = 6371e3; // metres
        double latitude1 = lat1 * Math.PI/180; // φ, λ in radians
        double latitude2 = lat2 * Math.PI/180;
        double deltaLatitude = (lat2-lat1) * Math.PI/180;
        double deltaLongitude = (lon2-lon1) * Math.PI/180;

        double a = Math.sin(deltaLatitude/2) * Math.sin(deltaLatitude/2) +
                Math.cos(latitude1) * Math.cos(latitude2) *
                        Math.sin(deltaLongitude/2) * Math.sin(deltaLongitude/2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return earthRadius * c; // in metres
    }

    public static List<ParkResponseDTO> sortedByDistance(List<ParkResponseDTO> responseDTOList) {
        return responseDTOList.stream()
                .sorted(Comparator.comparingDouble(ParkResponseDTO::getDistanceInMeters))
                .collect(Collectors.toList());
    }
}
