package fr.instantsystem.testapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkResponseDTO {
    String name;
    Double capacity;
    Double distanceInMeters;
    Double parkingSpotsAvailable;
}
