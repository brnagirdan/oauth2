package com.ecquaria.oauth2.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name=ResourceLinkEntity.TABLE_NAME)
public class ResourceLinkEntity implements Serializable {

    public static final String TABLE_NAME = "PRIVILEGE_RESOURCE_LINK";

    @Id
    @Column(name="LINK_NO")
    private Long linkNo;

    @Column(name="PRIVILEGE_NO")
    private Long privilegeNo;

    @Column(name="RESOURCE_TYPE")
    private String resourceType;

    @Column(name="RESOURCE_UID")
    private String resourceUid;

    @Column(name="REMARKS")
    private String remarks;

    @OneToOne
    @JoinColumn(name = "PRIVILEGE_NO", insertable = false, updatable = false)
    private PrivilegeEntity privilege;

    public Long getLinkNo() {
        return linkNo;
    }

    public void setLinkNo(Long linkNo) {
        this.linkNo = linkNo;
    }

    public Long getPrivilegeNo() {
        return privilegeNo;
    }

    public void setPrivilegeNo(Long privilegeNo) {
        this.privilegeNo = privilegeNo;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceUid() {
        return resourceUid;
    }

    public void setResourceUid(String resourceUid) {
        this.resourceUid = resourceUid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public PrivilegeEntity getPrivilege() {
        return privilege;
    }

    public void setPrivilege(PrivilegeEntity privilege) {
        this.privilege = privilege;
    }

}
