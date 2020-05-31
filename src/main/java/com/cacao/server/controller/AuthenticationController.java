package com.cacao.server.controller;

import com.cacao.server.model.Tokens;
import com.cacao.server.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/google")
    public ResponseEntity<Tokens> googleToken(@RequestParam String authCode){
        var tokens = authenticationService.retrieveTokens(authCode);
        if(tokens == null)
            return ResponseEntity.badRequest().body(null);
        else return ResponseEntity.ok(tokens);
    }

}
