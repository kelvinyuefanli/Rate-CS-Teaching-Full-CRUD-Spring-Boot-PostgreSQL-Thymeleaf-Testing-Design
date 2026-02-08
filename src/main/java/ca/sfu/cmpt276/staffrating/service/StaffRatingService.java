package ca.sfu.cmpt276.staffrating.service;

import ca.sfu.cmpt276.staffrating.exception.RatingNotFoundException;
import ca.sfu.cmpt276.staffrating.model.StaffRating;
import ca.sfu.cmpt276.staffrating.repository.StaffRatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class StaffRatingService {
    private final StaffRatingRepository repository;

    public StaffRatingService(StaffRatingRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<StaffRating> listAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public StaffRating getById(Long id) {
        Objects.requireNonNull(id, "id must not be null");
        return repository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("Rating not found"));
    }

    @Transactional
    public StaffRating saveRating(StaffRating rating) {
        Objects.requireNonNull(rating, "rating must not be null");
        return repository.save(rating);
    }

    @Transactional
    public void deleteRatingById(Long id) {
        Objects.requireNonNull(id, "id must not be null");
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean isEmailInUse(Long currentId, String email) {
        Objects.requireNonNull(email, "email must not be null");
        return repository.findByEmail(email)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .isPresent();
    }
}
