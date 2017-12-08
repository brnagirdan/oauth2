package com.ecquaria.oauth2.repository;

import com.ecquaria.oauth2.Entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity,Long>{

    @Query("select p from PrivilegeEntity p,RolePrivilegeEntity rp " +
            "where p.privilegeNo=rp.privilege.privilegeNo and rp.role.id=?1")
    List<PrivilegeEntity> findPrivilegesByRoleId(String role);
}
