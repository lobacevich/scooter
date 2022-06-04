package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.ScooterException;
import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.service.ScooterService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/scooters")
@AllArgsConstructor
public class ScooterController {

    private final ScooterService scooterService;
    private final ValidationErrorResponse validationErrorResponse;

    @GetMapping
    public List<ScooterDto> getAllScooters() {
        return scooterService.getAllScooters();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object createScooter(@Valid @RequestBody ScooterDto scooterDto,
                                BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return scooterService.createScooter(scooterDto);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object updateScooter(@PathVariable("id") Long id,@Valid @RequestBody ScooterDto scooterDto,
                               BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return scooterService.updateScooter(id, scooterDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse deleteScooter(@PathVariable("id") Long id) throws ScooterException {
        return scooterService.deleteScooter(id);
    }
}
