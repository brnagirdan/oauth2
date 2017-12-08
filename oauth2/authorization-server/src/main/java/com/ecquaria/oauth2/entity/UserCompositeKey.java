package com.ecquaria.oauth2.entity;

import java.io.Serializable;

public class UserCompositeKey implements Serializable{

    private String userDomain;
    private String userId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCompositeKey that = (UserCompositeKey) o;

        if (userDomain != null ? !userDomain.equals(that.userDomain) : that.userDomain != null) return false;
        return userId != null ? userId.equals(that.userId) : that.userId == null;

    }

    @Override
    public int hashCode() {
        int result = userDomain != null ? userDomain.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
