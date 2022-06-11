package com.a10.mejabelajar.dashboard.admin.service;


import com.a10.mejabelajar.auth.exception.RegistrationFieldEmptyException;
import com.a10.mejabelajar.auth.model.AdminRegistrationToken;
import com.a10.mejabelajar.auth.model.AdminRegistrationTokenDTO;
import com.a10.mejabelajar.auth.repository.TokenRepository;
import com.a10.mejabelajar.dashboard.admin.exception.EmptyTokenException;
import com.a10.mejabelajar.dashboard.admin.exception.TokenAlreadyGeneratedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminDashboardServiceImplTest {
    @InjectMocks
    private AdminDashboardServiceImpl dashboardService;

    @Mock
    private TokenRepository tokenRepository;

    private AdminRegistrationTokenDTO dto;
    private AdminRegistrationToken token;
    private static final String TOKEN = "abc";

    @BeforeEach
    void setUp(){
        dto = new AdminRegistrationTokenDTO();
        dto.setToken(TOKEN);
    }

    @Test
    void testGenerateToken(){
        AdminRegistrationToken token = dashboardService.generateToken(dto);
        verify(tokenRepository, times(1)).save(any(AdminRegistrationToken.class));
        verify(tokenRepository, atLeastOnce()).findByToken(dto.getToken());
        assertEquals(TOKEN, token.getToken());
    }

    @Test
    void testNotEmptyTokenValidation(){
        dto.setToken("");
        String dtoToken = dto.getToken();
        assertThrows(EmptyTokenException.class, ()-> dashboardService.validateNewToken(dtoToken));
    }

    @Test
    void testTokenNotAlreadyGeneratedValidation(){
        when(tokenRepository.findByToken(TOKEN)).thenReturn(new AdminRegistrationToken(TOKEN));
        String dtoToken = dto.getToken();
        assertThrows(TokenAlreadyGeneratedException.class, ()-> dashboardService.validateNewToken(dtoToken));
    }
}
