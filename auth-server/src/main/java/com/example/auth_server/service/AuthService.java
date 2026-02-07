package com.example.auth_server.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DefaultPfp;
import com.example.HasherUtil;
import com.example.TokenUtil;
import com.example.UserRole;
import com.example.auth_server.repository.AuthDao;
import com.example.entity.UserEntity;
import com.example.entity.UserProfileEntity;
import com.example.exception.AuthenticationException;
import com.example.exception.DatabaseConflictException;
import com.example.exception.InvalidCredentialsException;
import com.example.model.AuthResponse;
import com.example.model.LoginRequest;
import com.example.model.LoginResponse;
import com.example.model.RegisterRequest;

@Service
@SuppressWarnings("UnnecessaryReturnStatement")
public class AuthService {

    @Autowired
    private AuthDao authDao;
    @Autowired
    private TokenUtil jwtUtil;
    @Autowired
    private HasherUtil hasher;
    @Autowired
    private DefaultPfp defaultPfp;

    public LoginResponse validateLogin(LoginRequest login) throws AuthenticationException {
        var user = authDao.findByUsername(login.getUsername())
            .orElseThrow(()-> new AuthenticationException("Invalid Login Credentials"));
        if(!hasher.verifyPassword(user.getPasswordHash(), login.getPassword()))
        {
            throw new AuthenticationException("Invalid Login Credentials");            
        }

        String token = jwtUtil.makeToken(user.getUsername(), user.getId());
        LoginResponse loginResponse = new LoginResponse(user.getId(), user.getRole(), user.getUsername(), token);
        return loginResponse;
    }

    public void registerNewUser(RegisterRequest user) 
        throws InvalidCredentialsException, DatabaseConflictException
    {
        UserEntity entity = new UserEntity();

        //TODO check password requirements, email existence, etc.
        if (user.getPassword().length() < 8)
        {
            throw new InvalidCredentialsException();
        }

        //check existence
        if (authDao.findByUsername(user.getUsername()).isEmpty())
        {
            //already in db
            throw new DatabaseConflictException();
            //return ResponseEntity.status(409).build();
        }
        else
        {//potentially factor conversions into a seperate method

            //conversion from model to entity
            //dao.save will return an entity, guarenteed nonnull
            //this entity will have it's ID field filled in unlike the one that is passed into the function
            entity.setUsername(user.getUsername());
            entity.setEmail(user.getEmail());
            entity.setRole(UserRole.user); //default value

            //assign the password field in the entity
            String hash = hasher.hashPassword(user.getPassword());
            entity.setPasswordHash(hash);
            
            //create a corresponding profile
            UserProfileEntity profileEntity = new UserProfileEntity();
            profileEntity.setUserEntity(entity);
            profileEntity.setPfpEncoded(defaultPfp.get());
            entity.setUserProfile(profileEntity);
            
            authDao.save(entity);
            
            //success
            return;
        }
    }

    /*
    public AuthResponse validateToken(String token){
        if(jwtUtil.validateToken(token)){
            return new AuthResponse(token, true);
        }else{
            return new AuthResponse(token, false);
        }
    }*/

    public AuthResponse validateToken(String auth, UserRole requiredRole)
    {
        var token = jwtUtil.asToken(auth);
        if (!token.isValid() || token.isExpired())
            return new AuthResponse(auth, false);
        Optional<UserEntity> user = authDao.findById(token.getId());
        if (user.isEmpty())
            return new AuthResponse(auth, false);
        UserRole role = user.get().getRole();
        if (role.value < requiredRole.value)
            return new AuthResponse(auth, false);
        return new AuthResponse(auth, true);
    }
}
