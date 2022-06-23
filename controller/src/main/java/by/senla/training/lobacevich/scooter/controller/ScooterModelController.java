package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.ScooterModelDto;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.service.ScooterModelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/models")
@AllArgsConstructor
public class ScooterModelController {

    private final ScooterModelService scooterModelService;
    private final ValidationErrorResponse validationErrorResponse;

    @Operation(summary = "Get all models")
    @GetMapping
    public List<ScooterModelDto> getAllModels() {
        return scooterModelService.getAllModels();
    }

    @Operation(summary = "Add a model")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object createModel(@Valid @RequestBody ScooterModelDto modelDto,
                              BindingResult bindingResult) throws CreationException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return scooterModelService.createModel(modelDto);
    }

    @Operation(summary = "Update a model")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object updateModel(@PathVariable("id") Long id,@Valid @RequestBody ScooterModelDto modelDto,
                              BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return scooterModelService.updateModel(id, modelDto);
    }
}
