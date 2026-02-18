package com.example.model;

import java.util.Objects;

import com.example.UserRole;

public class LoginResponse {
    Integer id;
    Integer role;
    String username;
    String encryptedToken;

    public LoginResponse(Integer id, UserRole role, String username, String encryptedToken) {
        this.encryptedToken = encryptedToken;
        this.id = id;
        this.username = username;
        this.role = role.value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedToken() {
        return encryptedToken;
    }

    public void setEncryptedToken(String encryptedToken) {
        this.encryptedToken = encryptedToken;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.role);
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.encryptedToken);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LoginResponse other = (LoginResponse) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.encryptedToken, other.encryptedToken)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.role, other.role);
    }
}
