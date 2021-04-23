package com.vovarusskih72.bankcode.model;

public enum UserRoles {
    UNACTIVATED, USER, ADMIN, MODERATOR;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
