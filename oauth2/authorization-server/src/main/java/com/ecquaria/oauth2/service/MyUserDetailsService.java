package com.ecquaria.oauth2.service;

import com.ecquaria.oauth2.entity.MyUserDetails;
import com.ecquaria.oauth2.entity.UserRoleEntity;
import com.ecquaria.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleService userRoleService;

    //根据请求参数中的用户名来获取该用户的信息，如密码，role等，用于认证
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUserDetails myUserDetails=getUserDetails(username);
        return myUserDetails;
    }

    private MyUserDetails getUserDetails(String username){

        String[] sArr=username.split(":");
        if(sArr.length!=2){
            return null;
        }
        String userDomain=sArr[0];
        String userId=sArr[1];

        MyUserDetails myUserDetails=userRepository.findByUserDomainAndUserId(userDomain,userId);
        myUserDetails.setEnabled(true);
        myUserDetails.setAuthorities(findUserAuthorities(userDomain,userId));
        myUserDetails.setUsername(username);
        return myUserDetails;
    }

    private List<GrantedAuthority> findUserAuthorities(String userDomain,String userId){

        List<GrantedAuthority> authorities=new ArrayList();

        List<UserRoleEntity> userRoleEntityList=userRoleService.getAssignmentsByUser(userId,userDomain);
        if(!CollectionUtils.isEmpty(userRoleEntityList)){
            userRoleEntityList.stream().forEach(item->authorities.add(new SimpleGrantedAuthority("ROLE_"+item.getRoleId())));
        }
        System.out.println(authorities);
        return authorities;
    }

}
