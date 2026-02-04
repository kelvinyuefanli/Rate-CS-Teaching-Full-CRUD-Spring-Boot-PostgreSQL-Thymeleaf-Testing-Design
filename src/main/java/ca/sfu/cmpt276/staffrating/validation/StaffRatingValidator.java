package ca.sfu.cmpt276.staffrating.validation;

import ca.sfu.cmpt276.staffrating.model.StaffRating;
import ca.sfu.cmpt276.staffrating.service.StaffRatingService;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class StaffRatingValidator {
    private final StaffRatingService service;

    public StaffRatingValidator(StaffRatingService service) {
        this.service = service;
    }

    public void rejectDuplicateEmail(Long currentId, StaffRating rating, BindingResult bindingResult) {
        if (rating == null || rating.getEmail() == null) {
            return;
        }
        if (service.isEmailInUse(currentId, rating.getEmail())) {
            bindingResult.rejectValue("email", "duplicate", "Email must be unique");
        }
    }
}
