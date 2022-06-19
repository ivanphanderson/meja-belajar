package com.a10.mejabelajar.dashboard.admin.service;

import com.a10.mejabelajar.auth.model.AdminRegistrationToken;
import com.a10.mejabelajar.auth.model.AdminRegistrationTokenDto;
import com.a10.mejabelajar.auth.repository.TokenRepository;
import com.a10.mejabelajar.dashboard.admin.exception.EmptyTokenException;
import com.a10.mejabelajar.dashboard.admin.exception.TokenAlreadyGeneratedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public AdminRegistrationToken generateToken(AdminRegistrationTokenDto tokenDto) {
        validateNewToken(tokenDto.getToken());
        AdminRegistrationToken token = tokenRepository.findByToken(tokenDto.getToken());
        if (token != null && !token.isActive()) {
            token.setActive(true);
        } else {
            token = new AdminRegistrationToken(tokenDto.getToken());
        }
        tokenRepository.save(token);
        return token;
    }

    /**
     * Validate admin token.
     */
    public void validateNewToken(String newToken) {
        if (!validateTokenNotEmpty(newToken)) {
            throw new EmptyTokenException("Token can not be empty");
        }
        if (!validateTokenNotAlreadyGenerated(newToken)) {
            throw new TokenAlreadyGeneratedException("Token already generated");
        }
    }

    public boolean validateTokenNotEmpty(String newToken) {
        return !newToken.equals("");
    }

    public boolean validateTokenNotAlreadyGenerated(String newToken) {
        AdminRegistrationToken token = tokenRepository.findByToken(newToken);
        return token == null || !token.isActive();
    }
}
