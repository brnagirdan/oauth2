package com.ecquaria.oauth2.repository;

import com.ecquaria.oauth2.entity.MyUserDetails;
import com.ecquaria.oauth2.entity.UserCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MyUserDetails,UserCompositeKey>{

    MyUserDetails findByUserDomainAndUserId(String userDomain,String userId);

}
