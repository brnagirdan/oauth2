package com.ecquaria.oauth2.repository;

import com.ecquaria.oauth2.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Long>, JpaSpecificationExecutor<UserRoleEntity> {
}
