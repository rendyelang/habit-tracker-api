package com.rexxeagle.habittracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rexxeagle.habittracker.entity.RefreshToken;
import com.rexxeagle.habittracker.entity.Users;
import com.rexxeagle.habittracker.repository.RefreshTokenRepository;
import com.rexxeagle.habittracker.repository.UserRepository;

@Service
public class UserService extends AbstractUserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }
}
