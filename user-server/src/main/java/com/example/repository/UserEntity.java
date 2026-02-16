package com.example.repository;

import java.util.Objects;

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

    // auth server
    //@JdbcType(PostgreSQLEnumJdbcType.class)
    //private UserRole role;
    
    //unused
    private Boolean verifiedSeller;

    @OneToOne(mappedBy="userEntity", cascade=CascadeType.ALL)
    private UserProfileEntity userProfile;

    public UserEntity() {
    }

    public UserEntity(String email, Integer id, UserProfileEntity userProfile, String username, Boolean verifiedSeller) {
        this.email = email;
        this.id = id;
        this.userProfile = userProfile;
        this.username = username;
        this.verifiedSeller = verifiedSeller;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getVerifiedSeller() {
        return verifiedSeller;
    }

    public void setVerifiedSeller(Boolean verifiedSeller) {
        this.verifiedSeller = verifiedSeller;
    }

    public UserProfileEntity getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileEntity userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.username);
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.verifiedSeller);
        hash = 79 * hash + Objects.hashCode(this.userProfile);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.verifiedSeller, other.verifiedSeller)) {
            return false;
        }
        return Objects.equals(this.userProfile, other.userProfile);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserEntity{");
        sb.append("id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", email=").append(email);
        sb.append(", verifiedSeller=").append(verifiedSeller);
        sb.append(", userProfile=").append(userProfile);
        sb.append('}');
        return sb.toString();
    }


}
