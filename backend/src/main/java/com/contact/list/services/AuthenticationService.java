package com.contact.list.services;

import com.contact.list.api.request.AuthenticationRequest;
import com.contact.list.api.response.TokenResponse;
import com.contact.list.entities.User;
import com.contact.list.exceptions.IncorrectCredentialException;
import com.contact.list.repositories.UserRepository;
import com.contact.list.security.AuthenticatedUser;
import com.contact.list.security.JwtTokenHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenHandler jwtHandler;

    public TokenResponse authenticate(AuthenticationRequest authDto){
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtHandler.generateTokenFromUsername(userDetails.getUsername());
            return new TokenResponse(jwtToken);
        } catch (BadCredentialsException ex) {
            throw new IncorrectCredentialException();
        }

    }

    public AuthenticatedUser getAuthenticatedUser(){
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
