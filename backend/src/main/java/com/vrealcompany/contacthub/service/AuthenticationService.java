package com.vrealcompany.contacthub.service;

import com.vrealcompany.contacthub.model.dto.JwtAuthenticationResponse;
import com.vrealcompany.contacthub.model.dto.LoginRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signin(LoginRequest request);
}
