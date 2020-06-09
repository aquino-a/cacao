package com.cacao.server.service;

import com.cacao.server.model.User;
import com.cacao.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void AddUser(User user) {
        if(!userRepository.existsById(user.getId())){
            userRepository.save(user);
        }
        //TODO can add picture update
    }

    @Override
    public boolean exists(String id) {
        return userRepository.existsById(id);
    }
}
