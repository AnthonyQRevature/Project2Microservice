package com.example;

import com.example.clients.AuthClient;
import com.example.exception.ServiceUnavailableException;
import com.example.model.AuthResponse;

import feign.FeignException;

public class SecurityHelper {

    AuthClient client;

    public boolean validate(String auth, UserRole role)
        throws ServiceUnavailableException
    {
        try
        {
            AuthResponse response = client.verifyToken(auth, role.value);
            return response.isSuccess();
        }
        catch (FeignException e)
        {
            throw new ServiceUnavailableException(e);
        }
    }

    public boolean validate(String auth, Integer userId, UserRole role)
        throws ServiceUnavailableException
    {
        try
        {
            AuthResponse response = client.verifyToken(auth, userId, role.value);
            return response.isSuccess();
        }
        catch (FeignException e)
        {
            throw new ServiceUnavailableException(e);
        }
    }

    public SecurityHelper(AuthClient client)
    {
        this.client = client;
    }
}
