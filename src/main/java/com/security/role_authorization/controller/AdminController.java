package com.security.role_authorization.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public ResponseEntity<?> getAdmin() {
        return new ResponseEntity<>("This is admin only page",HttpStatus.OK);
    }
}
