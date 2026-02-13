package com.example.model;

import java.util.Objects;

public class UserResponse {
    int id;
    Integer role;
    String username;
    String email;
    Boolean verified_seller;
    ProfileResponse profile;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserResponse{");
        sb.append("id=").append(id);
        sb.append(", role=").append(role);
        sb.append(", username=").append(username);
        sb.append(", email=").append(email);
        sb.append(", verified_seller=").append(verified_seller);
        sb.append(", profile=").append(profile);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.role);
        hash = 29 * hash + Objects.hashCode(this.username);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.verified_seller);
        hash = 29 * hash + Objects.hashCode(this.profile);
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
        final UserResponse other = (UserResponse) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.verified_seller, other.verified_seller)) {
            return false;
        }
        return Objects.equals(this.profile, other.profile);
    }

    public static class ProfileResponse 
    {
        String pfp_encoded;
        String bio;
        Double latitude;
        Double longitude;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((pfp_encoded == null) ? 0 : pfp_encoded.hashCode());
            result = prime * result + ((bio == null) ? 0 : bio.hashCode());
            result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
            result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ProfileResponse other = (ProfileResponse) obj;
            if (pfp_encoded == null) {
                if (other.pfp_encoded != null)
                    return false;
            } else if (!pfp_encoded.equals(other.pfp_encoded))
                return false;
            if (bio == null) {
                if (other.bio != null)
                    return false;
            } else if (!bio.equals(other.bio))
                return false;
            if (latitude == null) {
                if (other.latitude != null)
                    return false;
            } else if (!latitude.equals(other.latitude))
                return false;
            if (longitude == null) {
                if (other.longitude != null)
                    return false;
            } else if (!longitude.equals(other.longitude))
                return false;
            return true;
        }

        public ProfileResponse(String bio, Double latitude, Double longitude, String pfp_encoded) {
            this.bio = bio;
            this.latitude = latitude;
            this.longitude = longitude;
            this.pfp_encoded = pfp_encoded;
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

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ProfileResponse{");
            sb.append("pfp_encoded=").append(pfp_encoded);
            sb.append(", bio=").append(bio);
            sb.append(", latitude=").append(latitude);
            sb.append(", longitude=").append(longitude);
            sb.append('}');
            return sb.toString();
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
