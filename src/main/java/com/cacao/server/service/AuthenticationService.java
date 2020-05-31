package com.cacao.server.service;

import com.cacao.server.model.Tokens;

public interface AuthenticationService {
    Tokens retrieveTokens(String authCode);
}
