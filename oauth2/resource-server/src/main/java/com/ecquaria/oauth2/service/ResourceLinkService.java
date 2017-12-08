package com.ecquaria.oauth2.service;


import com.ecquaria.oauth2.Entity.PrivilegeEntity;
import com.ecquaria.oauth2.repository.ResourceLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceLinkService {

    @Autowired
    private ResourceLinkRepository resourceLinkRepository;

    public PrivilegeEntity findPrivilegeByResourceUid(String resource){
        return resourceLinkRepository.findPrivilegeByResourceUid(resource);
    }

}
