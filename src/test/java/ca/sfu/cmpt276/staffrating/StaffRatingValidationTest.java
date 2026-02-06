package ca.sfu.cmpt276.staffrating;

import ca.sfu.cmpt276.staffrating.model.RoleType;
import ca.sfu.cmpt276.staffrating.model.StaffRating;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class StaffRatingValidationTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDownValidator() {
        validatorFactory.close();
    }

    @Test
    void invalidEmailRejected() {
        StaffRating rating = validRating();
        rating.setEmail("jhon3@gmai");

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void outOfRangeScoreRejected() {
        StaffRating rating = validRating();
        rating.setClarity(11);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("clarity"));
    }

    @Test
    void missingRequiredFieldsRejected() {
        StaffRating rating = new StaffRating();

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("roleType"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("clarity"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("niceness"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("knowledgeableScore"));
    }

    @Test
    void boundaryScoresAccepted() {
        StaffRating rating = validRating();
        rating.setClarity(1);
        rating.setNiceness(10);
        rating.setKnowledgeableScore(1);

        assertNoViolation(rating, "clarity");
        assertNoViolation(rating, "niceness");
        assertNoViolation(rating, "knowledgeableScore");
    }

    @Test
    void scoreBelowMinimumRejected() {
        StaffRating rating = validRating();
        rating.setNiceness(0);

        assertHasViolation(rating, "niceness");
    }

    @Test
    void nameLengthBoundariesEnforced() {
        StaffRating rating = validRating();
        rating.setName("A");
        assertHasViolation(rating, "name");

        rating.setName("AB");
        assertNoViolation(rating, "name");

        rating.setName("A".repeat(81));
        assertHasViolation(rating, "name");
    }

    @Test
    void commentLengthLimitEnforced() {
        StaffRating rating = validRating();
        rating.setComment("A".repeat(300));
        assertNoViolation(rating, "comment");

        rating.setComment("A".repeat(301));
        assertHasViolation(rating, "comment");
    }

    @Test
    void emailLengthLimitEnforced() {
        StaffRating rating = validRating();
        rating.setEmail("a".repeat(121) + "@example.com");

        assertHasViolation(rating, "email");
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

    private void assertHasViolation(StaffRating rating, String field) {
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals(field));
    }

    private void assertNoViolation(StaffRating rating, String field) {
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertThat(violations).noneMatch(v -> v.getPropertyPath().toString().equals(field));
    }
}
