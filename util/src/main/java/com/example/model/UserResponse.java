package com.example.model;

public class UserResponse {
    int id;
    String username;
    String email;
    Boolean verified_seller;
    ProfileResponse profile;

    public UserResponse() {
    }

    public UserResponse(String email, int id, ProfileResponse profile, String username, Boolean verified_seller) {
        this.email = email;
        this.id = id;
        this.profile = profile;
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

    public Boolean getVerified_seller() {
        return verified_seller;
    }

    public void setVerified_seller(Boolean verified_seller) {
        this.verified_seller = verified_seller;
    }

    public ProfileResponse getProfile() {
        return profile;
    }

    public void setProfile(ProfileResponse profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserResponse{");
        sb.append("id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", email=").append(email);
        sb.append(", verified_seller=").append(verified_seller);
        sb.append(", profile=").append(profile);
        sb.append('}');
        return sb.toString();
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((verified_seller == null) ? 0 : verified_seller.hashCode());
        result = prime * result + ((profile == null) ? 0 : profile.hashCode());
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
        UserResponse other = (UserResponse) obj;
        if (id != other.id)
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (verified_seller == null) {
            if (other.verified_seller != null)
                return false;
        } else if (!verified_seller.equals(other.verified_seller))
            return false;
        if (profile == null) {
            if (other.profile != null)
                return false;
        } else if (!profile.equals(other.profile))
            return false;
        return true;
    }

    
}
