package org.imures.cashregister.roles.repository;

import org.imures.cashregister.roles.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}