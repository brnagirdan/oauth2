package com.ecquaria.oauth2.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name=Role.TABLE_NAME)
public class Role implements Cloneable, Serializable {

    private static final long serialVersionUID = -5366557727919054009L;

    public static final String TABLE_NAME = "ROLE_TBL";

    @Id
    @Column(name="ROLE_ID")
    private String id;

    @Column(name="ROLE_NAME")
    private String name;

    @Column(name="IS_SYSTEM")
    private String isSystem;

    @Transient
    private HashMap attributeMap;

    public static final String VALUE_YES_CHAR = "Y";
    public static final String VALUE_NO_CHAR = "N";

    public String getAttribute(String name) {
        return (String) attributeMap.get(name);
    }

    public String[] getAllAttributeKeys() {
        return (String[]) attributeMap.keySet().toArray(
                new String[attributeMap.size()]);
    }

    public Map<String, String> getAllAttributes() {
        return (Map<String, String>) attributeMap.clone();
    }

    public boolean hasAttribute(String name) {
        return attributeMap.containsKey(name);
    }

    public String setAttribute(String name, String value) {
        return (String) attributeMap.put(name, value);
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




    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public boolean isSystemYes() {
        return isSystem != null &&
                isSystem.equals(VALUE_YES_CHAR);
    }

    public boolean isSystemNo() {
        return isSystem != null &&
                isSystem.equals(VALUE_NO_CHAR);
    }

    public HashMap<String, String> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(HashMap<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

}
