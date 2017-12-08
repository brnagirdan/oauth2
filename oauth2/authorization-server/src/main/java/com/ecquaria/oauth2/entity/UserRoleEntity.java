package com.ecquaria.oauth2.entity;

import javax.persistence.*;

@Entity
@Table(name="user_role_assign")
public class UserRoleEntity {

    @Id
    @Column(name = "ASSIGN_NO")
    private Long assignNo;

    @Column(name = "USER_DOMAIN")
    private String userDomain;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "PERMISSION")
    private String permission;

    @Column(name = "IS_SYSTEM")
    private Character isSystem;

    @OneToOne
    @JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)
    private Role role;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "USER_DOMAIN", referencedColumnName = "USER_DOMAIN", insertable = false, updatable = false),
            @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false) })
    private MyUserDetails user;

    public Long getAssignNo() {
        return assignNo;
    }

    public void setAssignNo(Long assignNo) {
        this.assignNo = assignNo;
    }

    public String getUserDomain() {
        return userDomain;
    }

    public void setUserDomain(String userDomain) {
        this.userDomain = userDomain;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Character getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Character isSystem) {
        this.isSystem = isSystem;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public MyUserDetails getUser() {
        return user;
    }

    public void setUser(MyUserDetails user) {
        this.user = user;
    }
}
