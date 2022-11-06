package fr.instantsystem.testapi.helper;

import fr.instantsystem.testapi.dto.ParkResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParkHelperTest {

    @Test
    @DisplayName("Test conversion WGS84 in meters")
    void conversionWgs84InMeters_shouldWorks_allArgumentsProvided() {
        Double actual = ParkHelper.haversineFormula(46.58572887486411,46.58708625124429,0.3428282528429566,0.34881537972539434);

        assertEquals(481.78755224407985, actual);
    }

    @Test
    @DisplayName("Test sorted by distance a DTO list")
    void sortedByDistanceDtoList_shouldWorks_allArgumentsProvided() {

        List<ParkResponseDTO> samples = new ArrayList<>();
        samples.add(new ParkResponseDTO("LIBERTE", 30.0, 481.78755224407985, null));
        samples.add(new ParkResponseDTO("NOTRE DAME", 642.0, 494.01127810671215, 242.0));
        samples.add(new ParkResponseDTO("CHASSEIGNE", 16.0, 245.88750606186701, null));


        List<ParkResponseDTO> actual = ParkHelper.sortedByDistance(samples);

        assertEquals(245.88750606186701, actual.get(0).getDistanceInMeters());
        assertEquals(481.78755224407985, actual.get(1).getDistanceInMeters());
        assertEquals(494.01127810671215, actual.get(2).getDistanceInMeters());
    }
}
