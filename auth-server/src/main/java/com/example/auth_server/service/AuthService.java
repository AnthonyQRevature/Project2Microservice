package com.example.auth_server.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HasherUtil;
import com.example.Marshaller;
import com.example.TokenUtil;
import com.example.TokenUtil.Token;
import com.example.UserRole;
import com.example.auth_server.repository.AuthDao;
import com.example.auth_server.repository.AuthEntity;
import com.example.exception.AuthenticationException;
import com.example.exception.DatabaseConflictException;
import com.example.exception.InvalidCredentialsException;
import com.example.model.AuthResponse;
import com.example.model.CredentialResponse;
import com.example.model.LoginRequest;
import com.example.model.LoginResponse;
import com.example.model.RegisterCredentialsRequest;

import jakarta.transaction.Transactional;

@Service
@SuppressWarnings({"UnnecessaryReturnStatement"})
public class AuthService {

    @Autowired
    private AuthDao authDao;
    @Autowired
    private TokenUtil jwtUtil;
    @Autowired
    private HasherUtil hasher;
    @Autowired
    public Marshaller<CredentialResponse, AuthEntity> credentialMarshaller;

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

    @Transactional
    public void registerNewUser(RegisterCredentialsRequest user) 
        throws InvalidCredentialsException, DatabaseConflictException
    {
        //TODO check password requirements, email existence, etc.
        if (user.getPassword().length() < 8)
        {
            throw new InvalidCredentialsException();
        }

        //check existence
        if (
            !authDao.findByUsername(user.getUsername()).isEmpty() || !authDao.findById(user.getId()).isEmpty())
        {
            //already in db
            throw new DatabaseConflictException();
        }
        else
        {
            String hashedPassword = hasher.hashPassword(user.getPassword());
            AuthEntity newAuth = new AuthEntity(user.getId(), hashedPassword, UserRole.user, user.getUsername());

            authDao.save(newAuth);
            
            //success
            return;
        }
    }

    public AuthResponse validateToken(String auth, Integer userId, UserRole requiredRole)
    {
        var token = jwtUtil.asToken(auth);
        if (token.getId() != userId)
        {
            return new AuthResponse(auth, false);
        }
        else
        {
            boolean valid = validate(token, requiredRole);
            return new AuthResponse(auth, valid);
        }
    }

    public AuthResponse validateToken(String auth, UserRole requiredRole)
    {
        var token = jwtUtil.asToken(auth);
        boolean valid = validate(token, requiredRole);
        return new AuthResponse(auth, valid);
    }

    public boolean validate(String auth, UserRole requiredRole)
    {
        var token = jwtUtil.asToken(auth);
        return validate(token, requiredRole);
    }

    public boolean validate(Token token, UserRole requiredRole)
    {
        if (!token.isValid() || token.isExpired()) return false;

        Optional<AuthEntity> user = authDao.findById(token.getId());
        if (user.isEmpty()) return false;
        
        UserRole role = user.get().getRole();
        
        if (role.value < requiredRole.value) 
            return false;
        else 
            return true;
    }

    public boolean updatePerms(Integer userId, Integer role) throws NoSuchElementException {
        AuthEntity entity = authDao.findById(userId).orElseThrow();
        entity.setRole(UserRole.of(role));
        return true;
    }

    public List<CredentialResponse> getAll() {
        return credentialMarshaller.convert(authDao.findAll());
    }
}
