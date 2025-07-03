package com.rexxeagle.habittracker.service;

import com.rexxeagle.habittracker.entity.Users;

// Abstaction implementation
public abstract class AbstractUserService {
    public abstract Users findByEmail(String email);
    public abstract Users saveUser(Users user);
    public abstract Users findById(Long id);
}
