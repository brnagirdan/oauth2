package com.ecquaria.oauth2.service;

import com.ecquaria.oauth2.Entity.PrivilegeEntity;
import com.ecquaria.oauth2.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public List<PrivilegeEntity> findPrivilegesByRoleId(String role){
        return privilegeRepository.findPrivilegesByRoleId(role);
    }

}
