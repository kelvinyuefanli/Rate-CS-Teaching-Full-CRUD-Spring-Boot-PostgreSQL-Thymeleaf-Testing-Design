package ca.sfu.cmpt276.staffrating.design;

import ca.sfu.cmpt276.staffrating.model.RoleType;

public class TaProfile implements StaffMemberProfile {
    @Override
    public String displayTitle() {
        return "Teaching Assistant";
    }

    @Override
    public RoleType supportedRole() {
        return RoleType.TA;
    }
}
