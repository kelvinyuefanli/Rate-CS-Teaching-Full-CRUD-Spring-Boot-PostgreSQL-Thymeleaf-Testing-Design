package ca.sfu.cmpt276.staffrating;

import ca.sfu.cmpt276.staffrating.design.*;
import ca.sfu.cmpt276.staffrating.model.RoleType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StaffProfileResolverTest {
    private static StaffProfileResolver resolver;

    @BeforeAll
    static void setup() {
        List<StaffMemberProfile> profiles = List.of(
                new TaProfile(),
                new ProfProfile(),
                new InstructorProfile(),
                new StaffProfile()
        );
        resolver = new StaffProfileResolver(profiles);
    }

    @Test
    void resolvesTeachingAssistant() {
        assertThat(resolver.displayTitle(RoleType.TA)).isEqualTo("Teaching Assistant");
    }

    @Test
    void resolvesProfessor() {
        assertThat(resolver.displayTitle(RoleType.PROF)).isEqualTo("Professor");
    }

    @Test
    void resolvesInstructor() {
        assertThat(resolver.displayTitle(RoleType.INSTRUCTOR)).isEqualTo("Instructor");
    }

    @Test
    void resolvesStaff() {
        assertThat(resolver.displayTitle(RoleType.STAFF)).isEqualTo("Staff");
    }

    @Test
    void nullRoleReturnsUnknown() {
        assertThat(resolver.displayTitle(null)).isEqualTo("Unknown");
    }
}
