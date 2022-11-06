package fr.instantsystem.testapi.controller;

import fr.instantsystem.testapi.dto.ParkResponseDTO;
import fr.instantsystem.testapi.exception.ParkNotFoundException;
import fr.instantsystem.testapi.service.ParkService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ParkController {
    @Autowired
    private ParkService parkService;

    /**
     * Read - Get all parks available
     * @return - An  list of objects of parks available sorted by distance in meters
     */
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success."),
            @ApiResponse(responseCode = "400", description = "Malformed request."),
            @ApiResponse(responseCode = "404", description = "City Not Found in our API."),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.")
    })
    @GetMapping("/parksAvailable")
    public List<ParkResponseDTO> getParksAvailable(@RequestParam String city, @RequestParam Double latitude, @RequestParam Double longitude) {
        try {
            return parkService.getParksAvailable(city, latitude, longitude);
        } catch (ParkNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "City Not Found in our API.", ex);
        }

    }
}
