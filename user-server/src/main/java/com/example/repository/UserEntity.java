package com.example.repository;

import java.util.Objects;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import com.example.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/*
 * Class Modeling the users table using JPA
 */
@Entity
@Table(name="users")
public class UserEntity {

    public UserProfileEntity getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileEntity userProfile) {
        this.userProfile = userProfile;
    }
    
    @Column(name="id")
    @Id
    /*
     * relies on a database's auto-increment feature
     */
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name="username")
    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    //Hibernate 6
    //otherwise we'd need to use native query
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private UserRole role;
    private Boolean verifiedSeller;

    @OneToOne(mappedBy="userEntity", cascade=CascadeType.ALL)
    private UserProfileEntity userProfile;

    public UserEntity() {
    }

    public UserEntity(String email, UserRole role, Integer user_id, String username, Boolean verifiedSeller) {
        this.email = email;
        this.role = role;
        this.id = user_id;
        this.username = username;
        this.verifiedSeller = verifiedSeller;
    }

    public UserEntity(String email, UserRole role, String username, Boolean verifiedSeller) {
        this.email = email;
        this.role = role;
        this.username = username;
        this.verifiedSeller = verifiedSeller;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer user_id) {
        this.id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getVerifiedSeller() {
        return verifiedSeller;
    }

    public void setVerifiedSeller(Boolean verifiedSeller) {
        this.verifiedSeller = verifiedSeller;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.username);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.role);
        hash = 29 * hash + Objects.hashCode(this.verifiedSeller);
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
        final UserEntity other = (UserEntity) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.verifiedSeller, other.verifiedSeller);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("userEntity{");
        sb.append("user_id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", email=").append(email);
        sb.append(", role=").append(role);
        sb.append(", verifiedSeller=").append(verifiedSeller);
        sb.append('}');
        return sb.toString();
    }
}
