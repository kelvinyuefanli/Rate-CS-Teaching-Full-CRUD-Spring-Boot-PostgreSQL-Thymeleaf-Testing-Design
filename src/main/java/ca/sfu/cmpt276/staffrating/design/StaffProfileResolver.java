package ca.sfu.cmpt276.staffrating.design;

import ca.sfu.cmpt276.staffrating.model.RoleType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StaffProfileResolver {
    private final List<StaffMemberProfile> profiles;

    public StaffProfileResolver() {
        this.profiles = List.of(
                new TaProfile(),
                new ProfProfile()
        );
    }

    public String displayTitle(RoleType roleType) {
        if (roleType == null) {
            return "Unknown";
        }
        return profiles.stream()
                .filter(profile -> profile.supportedRole() == roleType)
                .findFirst()
                .map(StaffMemberProfile::displayTitle)
                .orElse(roleType.name());
    }
}
