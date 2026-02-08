package ca.sfu.cmpt276.staffrating.design;

import ca.sfu.cmpt276.staffrating.model.RoleType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StaffProfileResolver {
    private final Map<RoleType, StaffMemberProfile> profileMap;

    public StaffProfileResolver(List<StaffMemberProfile> profiles) {
        this.profileMap = profiles.stream()
                .collect(Collectors.toUnmodifiableMap(
                        StaffMemberProfile::supportedRole,
                        profile -> profile,
                        (existing, ignored) -> existing
                ));
    }

    public String displayTitle(RoleType roleType) {
        if (roleType == null) {
            return "Unknown";
        }
        StaffMemberProfile profile = profileMap.get(roleType);
        return profile == null ? roleType.name() : profile.displayTitle();
    }
}
