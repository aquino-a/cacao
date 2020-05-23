package com.cacao.server.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController()
@RequestMapping("/api")
public class TestController {

    @GetMapping("me")
    public String whoAmi(@AuthenticationPrincipal JwtAuthenticationToken token){
        var jwt = (Jwt) token.getPrincipal();
        return jwt.getClaim("email");
    }
}
