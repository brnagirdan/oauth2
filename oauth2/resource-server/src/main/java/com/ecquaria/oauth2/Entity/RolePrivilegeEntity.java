package com.ecquaria.oauth2.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = RolePrivilegeEntity.TABLE_NAME)
public class RolePrivilegeEntity implements Serializable {

    public static final String TABLE_NAME = "ROLE_PRIVILEGE_ASSIGN";

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "PRIVILEGE_NO")
    private Long privilegeNo;

    @Id
    @Column(name = "ASSIGN_NO")
    private Long assignNo;

    @Column(name = "PERMISSION")
    private String permission;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "FROM_DATE")
    private Date fromDate;

    @Column(name = "TO_DATE")
    private Date toDate;

    @Column(name = "IS_SYSTEM")
    private String isSystem;

    @OneToOne
    @JoinColumn(name = "PRIVILEGE_NO", insertable = false, updatable = false)
    private PrivilegeEntity privilege;

    @OneToOne
    @JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Long getPrivilegeNo() {
        return privilegeNo;
    }

    public void setPrivilegeNo(Long privilegeNo) {
        this.privilegeNo = privilegeNo;
    }

    public Long getAssignNo() {
        return assignNo;
    }

    public void setAssignNo(Long assignNo) {
        this.assignNo = assignNo;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRemarks() {
        return remarks;
    }

    public PrivilegeEntity getPrivilege() {
        return privilege;
    }

    public void setPrivilege(PrivilegeEntity privilege) {
        this.privilege = privilege;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

}
