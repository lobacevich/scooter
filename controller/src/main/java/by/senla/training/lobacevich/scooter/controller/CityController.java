package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.dto.CityDto;
import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.service.CityService;
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
    private final ValidationErrorResponse validationErrorResponse;

    @GetMapping
    public List<CityDto> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("{id}/points")
    public List<PointDto> getCityPoints(@PathVariable("id") Long id) {
        return cityService.getCityPoints(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object createCity(@Valid @RequestBody CityDto cityDto, BindingResult bindingResult) throws CreationException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return cityService.createCity(cityDto);
    }
}
