package com.example.model;

import com.example.entity.UserEntity;
import com.example.entity.UserProfileEntity;

public class UserResponse {
    int id;
    Integer role;
    String username;
    String email;
    Boolean verified_seller;
    ProfileResponse profile;

    public static class ProfileResponse 
    {
        String pfp_encoded;
        String bio;
        Double latitude;
        Double longitude;

        public ProfileResponse(String bio, Double latitude, Double longitude, String pfp_encoded) {
            this.bio = bio;
            this.latitude = latitude;
            this.longitude = longitude;
            this.pfp_encoded = pfp_encoded;
        }

        public ProfileResponse(UserProfileEntity entity)
        {
            this(
                entity.getBio(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getPfpEncoded()
            );
        }

        public String getPfp_encoded() {
            return pfp_encoded;
        }
        public void setPfp_encoded(String pfp_encoded) {
            this.pfp_encoded = pfp_encoded;
        }

        public String getBio() {
            return bio;
        }
        public void setBio(String bio) {
            this.bio = bio;
        }

        public Double getLatitude() {
            return latitude;
        }
        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
        
        public Double getLongitude() {
            return longitude;
        }
        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }

    public UserResponse(String email, int id, ProfileResponse profile, Integer role, String username, Boolean verified_seller) {
        this.email = email;
        this.id = id;
        this.profile = profile;
        this.role = role;
        this.username = username;
        this.verified_seller = verified_seller;
    }

    public UserResponse(UserEntity entity)
    {
        this(
            entity.getEmail(), 
            entity.getId(), 
            new ProfileResponse(entity.getUserProfile()),
            entity.getRole().value,
            entity.getUsername(),
            entity.getVerifiedSeller()
        );
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
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

    public Boolean isVerified_seller() {
        return verified_seller;
    }
    public void setVerified_seller(Boolean verified_seller) {
        this.verified_seller = verified_seller;
    }

    public ProfileResponse getProfile() {
        return profile;
    }
    public void setProfile(ProfileResponse profileResponse) {
        this.profile = profileResponse;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
