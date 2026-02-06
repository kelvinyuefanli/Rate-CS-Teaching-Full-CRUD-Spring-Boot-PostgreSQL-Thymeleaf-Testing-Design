package ca.sfu.cmpt276.staffrating;

import ca.sfu.cmpt276.staffrating.model.RoleType;
import ca.sfu.cmpt276.staffrating.model.StaffRating;
import ca.sfu.cmpt276.staffrating.service.StaffRatingService;
import ca.sfu.cmpt276.staffrating.validation.StaffRatingValidator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StaffRatingValidatorTest {
    @Test
    void duplicateEmailAddsError() {
        StaffRatingService service = mock(StaffRatingService.class);
        StaffRatingValidator validator = new StaffRatingValidator(service);
        StaffRating rating = validRating();
        BindingResult result = new BeanPropertyBindingResult(rating, "rating");

        when(service.isEmailInUse(null, "ava@example.com")).thenReturn(true);

        validator.rejectDuplicateEmail(null, rating, result);

        assertThat(result.hasFieldErrors("email")).isTrue();
        verify(service).isEmailInUse(null, "ava@example.com");
    }

    private StaffRating validRating() {
        StaffRating rating = new StaffRating();
        rating.setName("Ava Example");
        rating.setEmail("ava@example.com");
        rating.setRoleType(RoleType.TA);
        rating.setClarity(8);
        rating.setNiceness(9);
        rating.setKnowledgeableScore(7);
        rating.setComment("Helpful and clear.");
        return rating;
    }
}
