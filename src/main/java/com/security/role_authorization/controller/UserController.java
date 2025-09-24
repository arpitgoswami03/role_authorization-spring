package com.security.role_authorization.controller;

import com.security.role_authorization.model.UserDTO;
import com.security.role_authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userCredentials) {
        if(userCredentials.getPassword().isBlank() || userCredentials.getUsername().isBlank()){
            return new ResponseEntity<>("Incomplete username or password", HttpStatus.BAD_REQUEST);
        }
        Boolean authenticated = userService.verify(userCredentials);
        return authenticated? new ResponseEntity<>("User verified", HttpStatus.OK) :
                new ResponseEntity<>("Incorrect username or password", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userCredentials){
        if(userCredentials.getUsername().isBlank() ||
                userCredentials.getPassword().isBlank() || userCredentials.getAge() <= 0){
            return new ResponseEntity<>("Incomplete details",  HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.createUser(userCredentials), HttpStatus.OK);
    }
}
