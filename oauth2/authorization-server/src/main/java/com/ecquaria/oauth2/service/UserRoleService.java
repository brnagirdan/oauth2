package com.ecquaria.oauth2.service;

import com.ecquaria.oauth2.entity.UserRoleEntity;
import com.ecquaria.oauth2.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRoleEntity> getAssignmentsByUser(final String userId, final String userDomain) {
        Specification<UserRoleEntity> spec = new Specification<UserRoleEntity>() {
            public Predicate toPredicate(Root<UserRoleEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate[] wheres={
                        cb.equal(root.get("userId"), userId),
                        cb.equal(root.get("userDomain"), userDomain)
                };
                Predicate where=cb.and(wheres);
                return where;
            }
        };

        List<UserRoleEntity> result = userRoleRepository.findAll(spec);
        return result;
    }

}
