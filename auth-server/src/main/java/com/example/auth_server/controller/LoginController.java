package com.example.auth_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.AllowCors;
import com.example.UserRole;
import com.example.auth_server.service.AuthService;
import com.example.exception.AuthenticationException;
import com.example.exception.DatabaseConflictException;
import com.example.exception.InvalidCredentialsException;
import com.example.model.AuthResponse;
import com.example.model.LoginRequest;
import com.example.model.LoginResponse;
import com.example.model.RegisterRequest;

/*
 * A Controller for the /login endpoint
 */
@RestController
@AllowCors
public class LoginController {

    AuthService service;

    /**
     * Login takes a LoginRequest when 
     * on success return some sort of session token to prove that the user logged in
     * on fail return some sort of failure.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest body)
    {
        try 
        {
            var response = service.validateLogin(body);
            
            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e)
        {
            return ResponseEntity.status(403).build();
        }
    }

    /**
     * RegisterUser takes a RegisterRequest and persists a UserEntity to the Database
     * returns a status code of 400 when either the username or password are invalid
     * returns a status code of 409 when a UserEntity with the same Username already exists in the database
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterRequest> registerUser(@RequestBody RegisterRequest body)
    {
        try {
            service.registerNewUser(body);
            return ResponseEntity.ok().build();
        }
        catch (InvalidCredentialsException e) {
            return ResponseEntity.status(400).build();
        }
        catch (DatabaseConflictException e) {
            return ResponseEntity.status(409).build();
        }
    }

    /*
    @GetMapping("/")
    public AuthResponse verifyToken(@RequestHeader("Authorization") String auth)
    {
        return service.validateToken(auth);
    }*/

    @GetMapping("/")
    public AuthResponse verifyToken(
        @RequestHeader("Authorization") String auth,
        @RequestParam(required=true) Integer userRoleLevel
    ) {
        return service.validateToken(auth, UserRole.of(userRoleLevel));
    }

    @Autowired
    public LoginController(AuthService authService) 
    { 
        this.service = authService; 
    }
}
