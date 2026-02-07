package com.manjith.service;

import com.manjith.entity.USER_ROLE;
import com.manjith.request.LoginRequest;
import com.manjith.responce.AuthResponse;
import com.manjith.responce.SignupRequest;

public interface AuthService  {
    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req) throws Exception;
}
