package com.example.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.security.auth.login.AccountNotFoundException;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AllowCors;
import com.example.SecurityHelper;
import com.example.UserRole;
import com.example.exception.ServiceUnavailableException;
import com.example.model.UserResponse;
import com.example.model.UserUpdateRequest;
import com.example.service.UserService;

@RestController
@AllowCors
@RequestMapping("/users")
public class UserController {
    
    SecurityHelper security;
    UserService userService;

    @GetMapping("/by-username/{username}")
    public ResponseEntity<?> getUserAndProfileByUsername(@PathVariable("username") String username) {
        try{
            UserResponse response = userService.findByUsername(username).orElseThrow();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserAndProfileById(@PathVariable("id") int id) {
        try{
            UserResponse response = userService.findById(id).orElseThrow();
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUserAndProfile(
        @RequestHeader("Authorization") String auth, 
        @PathVariable("id") Integer id, 
        @RequestBody UserUpdateRequest body
    ) {
        try{
            if (security.validate(auth, id, UserRole.user))
            {
                UserResponse response = userService.patchUserEntity(id, body);
                return ResponseEntity.ok(response);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (AccountNotFoundException e){
            return ResponseEntity.badRequest().body(e);
        }
        catch (ServiceUnavailableException e)
        {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserAndProfile(@RequestHeader("Authorization") String auth, @PathVariable("id") Integer id) {

        try{
            if (security.validate(auth, id, UserRole.super_user))
            {
                userService.deleteUserById(id);//on delete cascade
                return ResponseEntity.ok(id);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } 
        catch (ServiceUnavailableException e)
        {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getUsers(@RequestHeader("Authorization") String auth)
    {
        try
        {
            if (security.validate(auth, UserRole.super_user))
            {    
                List<UserResponse> users = userService.getAllUsers();
                return ResponseEntity.ok(users);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        catch (ServiceUnavailableException e)
        {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

    }

    @PatchMapping("/{id}/perms")
    public ResponseEntity<?> setPerms(
        @RequestHeader("Authorization") String auth,
        @PathVariable Integer id,
        @RequestBody Integer role
    ) {
        try
        {
            if (security.validate(auth, UserRole.super_user))
            {
                userService.setRole(id, UserRole.of(role));
                return ResponseEntity.ok().build();
            }
            else
            {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        catch (NoSuchElementException e)
        {
            //not present
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
        catch (ServiceUnavailableException e)
        {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e);
        }
    }

    @Autowired
    public UserController(UserService userService, SecurityHelper security)
    {
        this.userService = userService;
        this.security = security;
    }
}
