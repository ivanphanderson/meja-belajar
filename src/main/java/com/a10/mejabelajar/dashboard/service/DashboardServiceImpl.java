package com.a10.mejabelajar.dashboard.service;

import com.a10.mejabelajar.auth.model.AdminRegistrationToken;
import com.a10.mejabelajar.auth.model.AdminRegistrationTokenDTO;
import com.a10.mejabelajar.auth.repository.TokenRepository;
import com.a10.mejabelajar.dashboard.exception.EmptyTokenException;
import com.a10.mejabelajar.dashboard.exception.TokenAlreadyGeneratedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService{
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public AdminRegistrationToken generateToken(AdminRegistrationTokenDTO tokenDTO) {
        validateNewToken(tokenDTO.getToken());
        AdminRegistrationToken token = tokenRepository.findByToken(tokenDTO.getToken());
        if(token != null && !token.isActive()){
            token.setActive(true);
        }
        else {
            token = new AdminRegistrationToken(tokenDTO.getToken());
        }
        tokenRepository.save(token);
        return token;
    }

    public void validateNewToken(String newToken){
        if (!validateTokenNotEmpty(newToken)){
            throw new EmptyTokenException("Token can not be empty");
        }
        if (!validateTokenNotAlreadyGenerated(newToken)){
            throw new TokenAlreadyGeneratedException("Token already generated");
        }
    }

    public boolean validateTokenNotEmpty(String newToken){
        return !newToken.equals("");
    }

    public boolean validateTokenNotAlreadyGenerated(String newToken){
        AdminRegistrationToken token = tokenRepository.findByToken(newToken);
        return token == null || !token.isActive();
    }
}
