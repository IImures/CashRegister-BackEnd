package org.imures.cashregister.roles.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.imures.cashregister.roles.entity.Role;

@Getter
@Setter
@NoArgsConstructor
public class RoleResponse {
    private String authority;

    public RoleResponse(Role role){
        this.authority = role.getAuthority();
    }
}