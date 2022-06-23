package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.dto.CityDto;
import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.service.CityService;
import by.senla.training.lobacevich.scooter.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cities")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;
    private final PointService pointService;
    private final ValidationErrorResponse validationErrorResponse;

    @Operation(summary = "Get all cities")
    @GetMapping
    public List<CityDto> getAllCities() {
        return cityService.getAllCities();
    }

    @Operation(summary = "Get all points in the city")
    @GetMapping("{id}/points")
    public List<PointDto> getCityPoints(@PathVariable("id") Long id) {
        return pointService.getCityPoints(id);
    }

    @Operation(summary = "Add a city")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object createCity(@Valid @RequestBody CityDto cityDto, BindingResult bindingResult) throws CreationException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return cityService.createCity(cityDto);
    }
}
