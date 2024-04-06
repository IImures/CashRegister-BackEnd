package org.imures.cashregister.roles.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.imures.cashregister.users.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity(name = "roles")
@Data
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String authority;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @Override
    public String getAuthority() {
        return authority;
    }

}