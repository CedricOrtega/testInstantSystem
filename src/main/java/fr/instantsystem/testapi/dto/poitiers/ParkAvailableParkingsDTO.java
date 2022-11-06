package fr.instantsystem.testapi.dto.poitiers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkAvailableParkingsDTO {
    List<RecordsDTO> records;
}
