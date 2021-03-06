package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.TariffDto;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.service.TariffService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tariffs")
@AllArgsConstructor
public class TariffController {

    private final TariffService tariffService;
    private final ValidationErrorResponse validationErrorResponse;

    @Operation(summary = "Get all tariffs")
    @GetMapping
    public List<TariffDto> getAllTariffs() {
        return tariffService.getAllTariffs();
    }

    @Operation(summary = "Create a tariff")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object createTariff(@Valid @RequestBody TariffDto tariffDto,
                               BindingResult bindingResult) throws NotFoundException, CreationException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return tariffService.createTariff(tariffDto);
    }

    @Operation(summary = "Update a tariff")
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object updateTariff(@PathVariable("id") Long id,@Valid @RequestBody TariffDto tariffDto,
                              BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return tariffService.updateTariff(id, tariffDto);
    }
}
