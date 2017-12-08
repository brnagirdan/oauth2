package com.ecquaria.oauth2.repository;


import com.ecquaria.oauth2.Entity.PrivilegeEntity;
import com.ecquaria.oauth2.Entity.ResourceLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceLinkRepository extends JpaRepository<ResourceLinkEntity,Long>{

    @Query("select p from PrivilegeEntity p,ResourceLinkEntity r " +
            "where p.privilegeNo=r.privilegeNo and r.resourceUid=?1")
    PrivilegeEntity findPrivilegeByResourceUid(String resource);
}
