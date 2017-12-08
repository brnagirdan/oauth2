package com.ecquaria.oauth2.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = PrivilegeEntity.TABLE_NAME)
public class PrivilegeEntity implements Serializable {

    public static final String TABLE_NAME = "PRIVILEGE_TBL";

    public static final String VALUE_YES_CHAR = "Y";
    public static final String VALUE_NO_CHAR = "N";

    @Id
    @Column(name = "PRIVILEGE_NO")
    private Long privilegeNo;

    @Column(name = "PRIVILEGE_ID")
    private String id;

    @Column(name = "PRIVILEGE_NAME")
    private String name;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "IS_SYSTEM")
    private String isSystem;

    public Long getPrivilegeNo() {
        return privilegeNo;
    }

    public void setPrivilegeNo(Long privilegeNo) {
        this.privilegeNo = privilegeNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public boolean isSystemYes() {
        return isSystem != null && isSystem.equals(VALUE_YES_CHAR);
    }

    public boolean isSystemNo() {
        return isSystem != null && isSystem.equals(VALUE_NO_CHAR);
    }

}
