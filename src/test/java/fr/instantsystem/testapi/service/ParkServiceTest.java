package fr.instantsystem.testapi.service;

import fr.instantsystem.testapi.dto.ParkResponseDTO;
import fr.instantsystem.testapi.exception.ParkNotFoundException;
import fr.instantsystem.testapi.processor.poitiers.ParkProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ParkServiceTest {
    @Autowired
    ParkService parkService;

    @MockBean
    ParkProcessor parkProcessor;

    @Test
    @DisplayName("Test good API choiced")
    void pickGoodApi_shouldWorks_allArgumentsProvided() {
        List<ParkResponseDTO> samples = new ArrayList<>();
        samples.add(new ParkResponseDTO("LIBERTE", 30.0, 481.78755224407985, null));
        samples.add(new ParkResponseDTO("NOTRE DAME", 642.0, 494.01127810671215, 242.0));
        samples.add(new ParkResponseDTO("CHASSEIGNE", 16.0, 245.88750606186701, null));

        when(parkProcessor.getParksAvailablePoitiers(any(), any(),any(), any())).thenReturn(samples);

        List<ParkResponseDTO> actual = parkService.manageCityApi("poiTiers", 46.58708625124429, 0.34881537972539434);

        assertEquals(30.0, actual.get(0).getCapacity());
    }

    @Test
    @DisplayName("Test we don't find API and return empty list")
    void pickGoodApi_shouldNotFind_allArgumentsProvided() {
        List<ParkResponseDTO> samples = new ArrayList<>();
        samples.add(new ParkResponseDTO("LIBERTE", 30.0, 481.78755224407985, null));
        samples.add(new ParkResponseDTO("NOTRE DAME", 642.0, 494.01127810671215, 242.0));
        samples.add(new ParkResponseDTO("CHASSEIGNE", 16.0, 245.88750606186701, null));

        when(parkProcessor.getParksAvailablePoitiers(any(), any(),any(), any())).thenReturn(samples);

        List<ParkResponseDTO> actual = parkService.manageCityApi("poiddTiers", 46.58708625124429, 0.34881537972539434);

        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("Test we get parks available")
    void getParksAvailable_shouldWorks_allArgumentsProvided() throws ParkNotFoundException {
        List<ParkResponseDTO> samples = new ArrayList<>();
        samples.add(new ParkResponseDTO("LIBERTE", 30.0, 481.78755224407985, null));
        samples.add(new ParkResponseDTO("NOTRE DAME", 642.0, 494.01127810671215, 242.0));
        samples.add(new ParkResponseDTO("CHASSEIGNE", 16.0, 245.88750606186701, null));

        when(parkProcessor.getParksAvailablePoitiers(any(), any(),any(), any())).thenReturn(samples);

        List<ParkResponseDTO> actual = parkService.getParksAvailable("poiTiers", 46.58708625124429, 0.34881537972539434);

        assertEquals(30.0, actual.get(0).getCapacity());
    }

    @Test
    @DisplayName("Test we throw an exception")
    void getParksAvailable_shouldHaveError_allArgumentsProvided() throws ParkNotFoundException {
        assertThrows(ParkNotFoundException.class, () -> parkService.getParksAvailable("poiddTiers", 46.58708625124429, 0.34881537972539434));
    }
}
