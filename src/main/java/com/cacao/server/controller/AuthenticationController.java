package com.cacao.server.controller;

import com.cacao.server.model.Tokens;
import com.cacao.server.model.User;
import com.cacao.server.service.AuthenticationService;
import com.cacao.server.service.MessageService;
import com.cacao.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @Autowired
    private JwtDecoder jwtDecoder;

    /**
     * Takes a google authentication code and retrieves the token from google using the server's client id/secret.
     *
     * @param authCode
     * @return
     */
    @GetMapping("/google")
    public ResponseEntity<Tokens> googleToken(@RequestParam String authCode){
        var tokens = authenticationService.retrieveTokens(authCode);
        addNewUser(tokens.getIdToken());
        if(tokens == null)
            return ResponseEntity.badRequest().body(null);
        else return ResponseEntity.ok(tokens);
    }

    private void addNewUser(String idToken) {
        var jwt = jwtDecoder.decode((idToken));
        var user = User.fromJwt(jwt, null);

        userService.AddUser(user);
    }

}
