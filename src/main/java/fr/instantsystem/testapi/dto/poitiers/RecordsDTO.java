package fr.instantsystem.testapi.dto.poitiers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordsDTO {
    FieldsDTO fields;
}
