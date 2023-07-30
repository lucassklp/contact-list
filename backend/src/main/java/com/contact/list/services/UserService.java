package com.contact.list.services;


import com.contact.list.api.request.UserRegisterRequest;
import com.contact.list.exceptions.EmailAlreadyInUseException;
import com.contact.list.mappers.UserMapper;
import com.contact.list.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRegisterRequest userRegister){
        var registeredUser = userRepository.findByEmail(userRegister.getEmail());
        if(registeredUser.isPresent()){
            throw new EmailAlreadyInUseException();
        }

        var user = userMapper.toUser(userRegister);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
