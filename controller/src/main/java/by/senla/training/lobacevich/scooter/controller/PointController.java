package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.service.PointService;
import by.senla.training.lobacevich.scooter.service.ScooterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/points")
@AllArgsConstructor
public class PointController {

    private final PointService pointService;
    private final ScooterService scooterService;
    private final ValidationErrorResponse validationErrorResponse;

    @Operation(summary = "Get all points")
    @GetMapping
    public List<PointDto> getAllPoints(@RequestParam(value = "latitude", required = false) Integer latitude,
                                       @RequestParam(value = "longitude", required = false) Integer longitude) {
        return pointService.getPoints(latitude, longitude);
    }

    @Operation(summary = "Get all scooters in the point")
    @GetMapping("/{id}/scooters")
    public List<ScooterDto> getPointScooters(@PathVariable("id") Long pointId) throws NotFoundException {
        return scooterService.getPointScooters(pointId);
    }

    @Operation(summary = "Create a point")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object createPoint(@Valid @RequestBody PointDto pointDto, BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return pointService.createPoint(pointDto);
    }

    @Operation(summary = "Update a point")
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object updatePoint(@PathVariable("id") Long id, @Valid @RequestBody PointDto pointDto,
                              BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return pointService.updatePoint(id, pointDto);
    }

    @Operation(summary = "Delete a point")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse deletePoint(@PathVariable("id") Long id) {
        return pointService.deletePoint(id);
    }
}
