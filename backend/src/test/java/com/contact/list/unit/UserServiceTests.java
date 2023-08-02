package com.contact.list.unit;

import com.contact.list.entities.User;
import com.contact.list.exceptions.EmailAlreadyInUseException;
import com.contact.list.mappers.UserMapperImpl;
import com.contact.list.repositories.UserRepository;
import com.contact.list.services.UserService;
import com.contact.list.utils.MockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    private UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup(){
        userService = new UserService(
                userRepository,
                new UserMapperImpl(),
                passwordEncoder
        );
    }


    @Test
    void registerSuccessfully(){
        var user = MockData.createUserRequest(MockData.ALEX);

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(user.getPassword()))
                .thenReturn("encrypted-password");

        userService.register(user);
    }

    @Test
    void registerWithAnExistentUser(){
        var user = MockData.createUserRequest(MockData.ALEX);

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyInUseException.class, () -> userService.register(user));
    }

}
