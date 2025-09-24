package com.security.role_authorization.service;

import com.security.role_authorization.model.Role;
import com.security.role_authorization.model.UserDTO;
import com.security.role_authorization.model.Users;
import com.security.role_authorization.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public Boolean verify(UserDTO userCredentials) {
        Users user = userRepo.findByUsername(userCredentials.getUsername());
        if(user==null){
            return false;
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userCredentials.getUsername(),
                            userCredentials.getPassword()
                    )
            );
            System.out.println(authentication.getAuthorities());
            return authentication.isAuthenticated();
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return false;
        }
    }

    public String createUser(UserDTO userCredentials) {
        if(userRepo.findByUsername(userCredentials.getUsername())!=null) {
            return "Username already exists";
        }
        Users user = new Users();
        user.setUsername(userCredentials.getUsername());
        user.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        if(userCredentials.getAge() <= 18) {
            user.setRole(List.of(Role.TEEN));
        }
        else{
            user.setRole(List.of(Role.TEEN, Role.ADULT));
        }
        userRepo.save(user);
        return "User created";
    }
}
