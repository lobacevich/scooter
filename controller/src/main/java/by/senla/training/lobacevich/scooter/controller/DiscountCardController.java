package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.DiscountCardDto;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.service.DiscountCardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/discounts")
@AllArgsConstructor
public class DiscountCardController {

    private final DiscountCardService discountCardService;
    private final ValidationErrorResponse validationErrorResponse;

    @Operation(summary = "Get current user discount card")
    @GetMapping
    public Object getCard(Principal principal) throws NotFoundException {
        return discountCardService.getDiscountCard(principal);
    }

    @Operation(summary = "Give discount card to user")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object giveCardToUser(@RequestParam(value = "userId") Long userId,
                                          @Valid @RequestBody DiscountCardDto discountCardDto,
                                          BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return discountCardService.addDiscountCard(discountCardDto, userId);
    }

    @Operation(summary = "Update user's discount card")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object updateUserCard(@RequestParam(value = "userId") Long userId,
                                 @Valid @RequestBody DiscountCardDto discountCardDto,
                                 BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return discountCardService.updateDiscountCard(discountCardDto, userId);
    }
}
