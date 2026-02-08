package ca.sfu.cmpt276.staffrating.design;

import ca.sfu.cmpt276.staffrating.model.RoleType;
import org.springframework.stereotype.Component;

@Component
public class StaffProfile implements StaffMemberProfile {
    @Override
    public String displayTitle() {
        return "Staff";
    }

    @Override
    public RoleType supportedRole() {
        return RoleType.STAFF;
    }
}
