package ca.sfu.cmpt276.staffrating.design;

import ca.sfu.cmpt276.staffrating.model.RoleType;

public interface StaffMemberProfile {
    String displayTitle();
    RoleType supportedRole();
}
