package com.cip.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ENGINEER,
    OPERATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
