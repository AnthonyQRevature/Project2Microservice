package com.example.auth_server.repository;

import java.util.Objects;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import com.example.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="credentials")
public class AuthEntity {
    @Id
    Integer id;
    @JdbcType(PostgreSQLEnumJdbcType.class)
    UserRole role;
    String username;
    String passwordHash;

    public AuthEntity(Integer id, String passwordHash, UserRole role, String username) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.role = role;
        this.username = username;
    }

    public AuthEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.role);
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.passwordHash);
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
        final AuthEntity other = (AuthEntity) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return this.role == other.role;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AuthEntity{");
        sb.append("id=").append(id);
        sb.append(", role=").append(role);
        sb.append(", username=").append(username);
        sb.append(", passwordHash=").append(passwordHash);
        sb.append('}');
        return sb.toString();
    }
}
