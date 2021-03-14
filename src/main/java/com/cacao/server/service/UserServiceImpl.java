package com.cacao.server.service;

import com.cacao.server.model.User;
import com.cacao.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Value("${user.ownerId}")
    String ownerId;

    @Transactional
    @Override
    public void AddUser(User user) {
        if(!userRepository.existsById(user.getId())){
            var u = userRepository.save(user);
            u.getFriends().add(u);

            var owner = userRepository.findById(ownerId);
            owner.ifPresent(o -> u.getFriends().add(o));
        }
        //TODO can add picture update
    }

    @Override
    public boolean exists(String id) {
        return userRepository.existsById(id);
    }

    @Transactional
    @Override
    public void addFriend(String userId, String email) {
        var optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user ->{
            var optionalFriend = userRepository.findByEmail(email);
            optionalFriend.ifPresent(friend ->{
                user.getFriends().add(optionalFriend.get());
            });
        });
    }

    @Override
    public Optional<User> findUser(String id) {
        return userRepository.findById(id);
    }
}
