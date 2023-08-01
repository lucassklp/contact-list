package com.contact.list.controllers;

import com.contact.list.api.request.AuthenticationRequest;
import com.contact.list.api.response.TokenResponse;
import com.contact.list.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping
    @SecurityRequirements
    public ResponseEntity<TokenResponse> login(@RequestBody @Validated AuthenticationRequest authenticationRequestDto){
        var token = authenticationService.authenticate(authenticationRequestDto);
        return ResponseEntity.ok()
                .body(token);
    }
}
