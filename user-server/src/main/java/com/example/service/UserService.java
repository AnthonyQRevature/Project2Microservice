package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.DefaultPfp;
import com.example.Marshaller;
import com.example.clients.AuthClient;
import com.example.exception.DatabaseConflictException;
import com.example.model.RegisterCredentialsRequest;
import com.example.model.RegisterRequest;
import com.example.model.UserResponse;
import com.example.model.UserUpdateRequest;
import com.example.repository.UserDao;
import com.example.repository.UserEntity;
import com.example.repository.UserProfileEntity;

import feign.FeignException;
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
            e.getUsername(),
            e.getVerifiedSeller()
        );
    });
    
    @Autowired
    AuthClient auth;
    @Autowired
    DefaultPfp defaultPfp;
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

    public boolean registerNewUser(RegisterRequest request) 
        throws DatabaseConflictException, FeignException
    {
        //check for conflict
        if (dao.existsByUsername(request.getUsername()))
        {
            throw new DatabaseConflictException();
        }

        RegisterCredentialsRequest newCredentials = new RegisterCredentialsRequest();
        newCredentials.setUsername(request.getUsername());
        newCredentials.setPassword(request.getPassword());

        var response = auth.registerUser(newCredentials);

        if (response.getStatusCode() != HttpStatus.OK)
        {
            return false;
        }

        //should replace with a factory pattern
        UserEntity unmanaged = new UserEntity(
            request.getEmail(),
            null,
            new UserProfileEntity(
                null,
                null,
                null,
                null,
                null,
                defaultPfp.get()
            ),
            request.getUsername(),
            false
        );
        dao.save(unmanaged);

        return true;
    }

    //achieves constructor injection
    @Autowired
    public UserService(
        UserDao dao
    ) {
        this.dao = dao;
    }
}
