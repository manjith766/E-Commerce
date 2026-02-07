package com.manjith.service.impl;

import com.manjith.config.JwtProvider;
import com.manjith.entity.User;
import com.manjith.repository.UserRepository;
import com.manjith.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

         return this.findUserByEmail(email);

    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);
        if (user== null){
            throw new Exception("user not found with email -"+email);
        }
        return user;
    }
}
