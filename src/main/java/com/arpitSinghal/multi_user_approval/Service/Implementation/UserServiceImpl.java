package com.arpitSinghal.multi_user_approval.Service.Implementation;

import com.arpitSinghal.multi_user_approval.Entity.User;
import com.arpitSinghal.multi_user_approval.Repository.UserRepository;
import com.arpitSinghal.multi_user_approval.Service.UserService;
import com.arpitSinghal.multi_user_approval.dto.LoginRequest;
import com.arpitSinghal.multi_user_approval.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public ResponseEntity<?> signup(SignupRequest req) {
        if (userRepo.findByEmail(req.email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPassword(req.password);
        userRepo.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully", "userId", user.getId()));
    }

    @Override
    public ResponseEntity<?> login(LoginRequest req) {
        Optional<User> user = userRepo.findByEmail(req.email);
        if (user.isPresent() && user.get().getPassword().equals(req.password)) {
            return ResponseEntity.ok(Map.of( "userId", user.get().getId(),"userName",user.get().getName(),"message","Login successful"));
        }
        return ResponseEntity.status(401).body("Invalid Login credentials");
    }
}
