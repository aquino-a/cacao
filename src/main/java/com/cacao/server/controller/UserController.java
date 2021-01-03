package com.cacao.server.controller;

import com.cacao.server.model.User;
import com.cacao.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/friend/add")
    public ResponseEntity addFriend(@AuthenticationPrincipal User user, @RequestParam String email){
        userService.addFriend(user.getId(), email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/friends")
    public ResponseEntity<Set<User>> friends(@AuthenticationPrincipal User user){
        var optionalUser = userService.findUser(user.getId());
        if(optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get().getFriends());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
