package ca.sfu.cmpt276.staffrating;

import ca.sfu.cmpt276.staffrating.model.RoleType;
import ca.sfu.cmpt276.staffrating.model.StaffRating;
import ca.sfu.cmpt276.staffrating.repository.StaffRatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StaffRatingRepositoryTest {
    @Autowired
    private StaffRatingRepository repository;

    @Test
    void savingAndRetrievingWorks() {
        StaffRating rating = buildRating("kim@example.com");
        StaffRating saved = repository.save(rating);

        StaffRating found = repository.findById(saved.getId()).orElseThrow();
        assertThat(found.getEmail()).isEqualTo("kim@example.com");
    }

    @Test
    void deleteRemovesEntry() {
        StaffRating rating = buildRating("leo@example.com");
        StaffRating saved = repository.save(rating);

        repository.deleteById(saved.getId());
        assertThat(repository.findById(saved.getId())).isEmpty();
    }

    private StaffRating buildRating(String email) {
        StaffRating rating = new StaffRating();
        rating.setName("Kim Student");
        rating.setEmail(email);
        rating.setRoleType(RoleType.PROF);
        rating.setClarity(9);
        rating.setNiceness(8);
        rating.setKnowledgeableScore(10);
        rating.setComment("Great lectures.");
        return rating;
    }
}
