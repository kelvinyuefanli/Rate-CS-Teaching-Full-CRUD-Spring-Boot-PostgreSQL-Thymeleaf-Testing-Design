package ca.sfu.cmpt276.staffrating.design;

import ca.sfu.cmpt276.staffrating.model.RoleType;

public class ProfProfile implements StaffMemberProfile {
    @Override
    public String displayTitle() {
        return "Professor";
    }

    @Override
    public RoleType supportedRole() {
        return RoleType.PROF;
    }
}
