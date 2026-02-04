package ca.sfu.cmpt276.staffrating.service;

import ca.sfu.cmpt276.staffrating.exception.RatingNotFoundException;
import ca.sfu.cmpt276.staffrating.model.StaffRating;
import ca.sfu.cmpt276.staffrating.repository.StaffRatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StaffRatingService {
    private final StaffRatingRepository repository;

    public StaffRatingService(StaffRatingRepository repository) {
        this.repository = repository;
    }

    public List<StaffRating> listAll() {
        return repository.findAll();
    }

    public StaffRating getById(Long id) {
        Objects.requireNonNull(id, "id must not be null");
        return repository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("Rating not found"));
    }

    public StaffRating saveRating(StaffRating rating) {
        Objects.requireNonNull(rating, "rating must not be null");
        return repository.save(rating);
    }

    public void deleteRatingById(Long id) {
        Objects.requireNonNull(id, "id must not be null");
        repository.deleteById(id);
    }

    public boolean isEmailInUse(Long currentId, String email) {
        Objects.requireNonNull(email, "email must not be null");
        return repository.findByEmail(email)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .isPresent();
    }
}
