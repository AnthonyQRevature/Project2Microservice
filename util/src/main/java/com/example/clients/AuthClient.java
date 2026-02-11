package com.example.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.AuthResponse;

@FeignClient(name="auth-service", path="auth")
public interface AuthClient {
    @GetMapping("")
    AuthResponse verifyToken(
        @RequestHeader("Authorization") String auth,
        @RequestParam(required=true) Integer userRoleLevel
    );

    @GetMapping("")
    AuthResponse verifyToken(
        @RequestHeader("Authorization") String auth,
        @RequestParam Integer userId,
        @RequestParam Integer userRoleLevel
    );
}
