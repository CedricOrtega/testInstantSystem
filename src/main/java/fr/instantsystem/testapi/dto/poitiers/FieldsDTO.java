package fr.instantsystem.testapi.dto.poitiers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldsDTO {
    Double capacite;
    Double places_restantes;
    Double nb_places;
    String nom;
    List<Double> geo_point_2d;
}
