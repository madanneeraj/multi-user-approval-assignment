package com.arpitSinghal.multi_user_approval.Service;

import com.arpitSinghal.multi_user_approval.Entity.User;
import com.arpitSinghal.multi_user_approval.dto.LoginRequest;
import com.arpitSinghal.multi_user_approval.dto.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> signup(SignupRequest req);
    ResponseEntity<?> login(LoginRequest req);
}
