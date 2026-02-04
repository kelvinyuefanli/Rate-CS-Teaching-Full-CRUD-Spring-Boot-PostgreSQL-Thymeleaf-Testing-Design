package ca.sfu.cmpt276.staffrating.repository;

import ca.sfu.cmpt276.staffrating.model.StaffRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRatingRepository extends JpaRepository<StaffRating, Long> {
    Optional<StaffRating> findByEmail(String email);
}
