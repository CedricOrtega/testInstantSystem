package fr.instantsystem.testapi.processor.poitiers;

import fr.instantsystem.testapi.connector.GrandPoitiersClient;
import fr.instantsystem.testapi.constant.ConstantOpenAPIEndpoint;
import fr.instantsystem.testapi.dto.ParkResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ParkProcessorTest {
    @Autowired
    ParkProcessor parkProcessor;

    @MockBean
    GrandPoitiersClient grandPoitiersClient;

    LinkedHashMap<String, Object> test = new LinkedHashMap<>();
    LinkedHashMap<String, Object> test2 = new LinkedHashMap<>();

    @BeforeEach
    void init(){
        // mock first call api response

        List<LinkedHashMap> recordList = new ArrayList<>();
        LinkedHashMap<String, Object> mapRecord = new LinkedHashMap<>();
        List<LinkedHashMap> fieldsList = new ArrayList<>();
        LinkedHashMap<String, Object> mapField = new LinkedHashMap<>();
        mapField.put("places_restantes" ,359.0);
        mapField.put("capacite" ,665.0);
        mapField.put("nom" ,"BLOSSAC TISON");
        mapRecord.put("fields", mapField);
        fieldsList.add(mapField);
        recordList.add(mapRecord);
        test.put("records", recordList);

        // mock second call api response

        List<LinkedHashMap> recordList2 = new ArrayList<>();
        LinkedHashMap<String, Object> mapRecord2 = new LinkedHashMap<>();
        List<LinkedHashMap> fieldsList2 = new ArrayList<>();
        LinkedHashMap<String, Object> mapField2 = new LinkedHashMap<>();
        List<Double> geoPoint2d = new ArrayList<>();
        geoPoint2d.add(46.57505317559496);
        geoPoint2d.add(0.337126307915689);
        mapField2.put("geo_point_2d" , geoPoint2d);
        mapField2.put("nom" , "BLOSSAC TISON");
        mapRecord2.put("fields", mapField2);
        fieldsList2.add(mapField2);
        recordList2.add(mapRecord2);
        test2.put("records", recordList2);
    }

    @Test
    @DisplayName("Test get park available Poitiers")
    void sortedByDistanceDtoList_shouldWorks_allArgumentsProvided() {
        when(grandPoitiersClient.genericGetParkings(URI.create(ConstantOpenAPIEndpoint.DATA_GRAND_POITIERS_AVAILABLE_PARKINGS))).thenReturn(test);
        when(grandPoitiersClient.genericGetParkings(URI.create(ConstantOpenAPIEndpoint.DATA_GRAND_POITIERS_LIST_PARKINGS))).thenReturn(test2);

        List<ParkResponseDTO> actual = parkProcessor.getParksAvailablePoitiers(URI.create(ConstantOpenAPIEndpoint.DATA_GRAND_POITIERS_LIST_PARKINGS),
                URI.create(ConstantOpenAPIEndpoint.DATA_GRAND_POITIERS_AVAILABLE_PARKINGS),
                46.58708625124429, 0.34881537972539434);

        assertAll(
                () -> assertEquals("BLOSSAC TISON" ,actual.get(0).getName()),
                () -> assertEquals(665.0, actual.get(0).getCapacity()),
                () -> assertEquals( 1608.847316994258, actual.get(0).getDistanceInMeters()),
                () -> assertEquals(359.0, actual.get(0).getParkingSpotsAvailable())
        );
    }
}
