package com.a10.mejabelajar.dashboard.service;

import com.a10.mejabelajar.auth.model.AdminRegistrationToken;
import com.a10.mejabelajar.auth.model.AdminRegistrationTokenDTO;

public interface DashboardService {
    AdminRegistrationToken generateToken(AdminRegistrationTokenDTO tokenDTO);
}
