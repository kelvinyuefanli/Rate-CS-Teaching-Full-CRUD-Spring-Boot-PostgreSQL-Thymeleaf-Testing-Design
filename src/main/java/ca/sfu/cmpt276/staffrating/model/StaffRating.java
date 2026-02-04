package ca.sfu.cmpt276.staffrating.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "staff_ratings", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class StaffRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 80, message = "Name must be 2-80 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(
            message = "Email must be valid",
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )
    @Size(max = 120, message = "Email must be at most 120 characters")
    private String email;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @NotNull(message = "Clarity is required")
    @Min(value = 1, message = "Clarity must be 1-10")
    @Max(value = 10, message = "Clarity must be 1-10")
    private Integer clarity;

    @NotNull(message = "Niceness is required")
    @Min(value = 1, message = "Niceness must be 1-10")
    @Max(value = 10, message = "Niceness must be 1-10")
    private Integer niceness;

    @NotNull(message = "Knowledgeable score is required")
    @Min(value = 1, message = "Knowledgeable score must be 1-10")
    @Max(value = 10, message = "Knowledgeable score must be 1-10")
    private Integer knowledgeableScore;

    @Size(max = 300, message = "Comment must be at most 300 characters")
    private String comment;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Integer getClarity() {
        return clarity;
    }

    public void setClarity(Integer clarity) {
        this.clarity = clarity;
    }

    public Integer getNiceness() {
        return niceness;
    }

    public void setNiceness(Integer niceness) {
        this.niceness = niceness;
    }

    public Integer getKnowledgeableScore() {
        return knowledgeableScore;
    }

    public void setKnowledgeableScore(Integer knowledgeableScore) {
        this.knowledgeableScore = knowledgeableScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public double getOverallScore() {
        if (clarity == null || niceness == null || knowledgeableScore == null) {
            return 0.0;
        }
        return (clarity + niceness + knowledgeableScore) / 3.0;
    }
}
