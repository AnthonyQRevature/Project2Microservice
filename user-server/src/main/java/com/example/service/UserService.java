package com.example.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Marshaller;
import com.example.UserRole;
import com.example.model.UserResponse;
import com.example.model.UserUpdateRequest;
import com.example.repository.UserDao;
import com.example.repository.UserEntity;
import com.example.repository.UserProfileEntity;

import jakarta.transaction.Transactional;

/*
 * a service class bean
 */
@Service
public class UserService {

    Marshaller<UserResponse.ProfileResponse, UserProfileEntity> profileMarshaller = new Marshaller<>((e) -> {
        return new UserResponse.ProfileResponse(
            e.getBio(),
            e.getLatitude(),
            e.getLongitude(),
            e.getPfpEncoded()
        );
    });
    Marshaller<UserResponse, UserEntity> marshaller = new Marshaller<>((e) -> {
        return new UserResponse(
            e.getEmail(),
            e.getId(),
            profileMarshaller.convert(e.getUserProfile()),
            e.getRole().value,
            e.getUsername(),
            e.getVerifiedSeller()
        );
    });
    UserDao dao;

    @Transactional
    public UserResponse patchUserEntity(Integer id, UserUpdateRequest body) throws AccountNotFoundException {
        //check existence
        UserEntity entity = dao.findById(id).orElseThrow(() -> new AccountNotFoundException());

        if (body.getEmail() != null) entity.setEmail(body.getEmail());
        if (body.getProfile() != null)
        {
            if (body.getProfile().getBio() != null) entity.getUserProfile().setBio(body.getProfile().getBio());
            if (body.getProfile().getLatitude() != null) entity.getUserProfile().setLatitude(body.getProfile().getLatitude());
            if (body.getProfile().getLongitude() != null) entity.getUserProfile().setLongitude(body.getProfile().getLongitude());
        }

        return marshaller.convert(entity);
    }
    
    public Optional<UserResponse> findByUsername(String username){
        return marshaller.convert(dao.findUserByUsername(username));
    }

    public boolean deleteUserById(int id){
        boolean exists = dao.existsById(id);
        if (exists)
            dao.deleteById(id);
        return exists;
    }

    public List<UserResponse> getAllUsers() {
        return marshaller.convert(dao.findAll());
    }

    public Optional<UserResponse> findById(Integer id) {
        return marshaller.convert(dao.findById(id));
    }

    @Transactional
    public boolean setRole(Integer userId, UserRole role)
    {
        try
        {
            UserEntity entity = dao.findById(userId).orElseThrow();
            entity.setRole(role);
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }

    }

    //achieves constructor injection
    @Autowired
    public UserService(
        UserDao dao
    ) {
        this.dao = dao;
    }
}
