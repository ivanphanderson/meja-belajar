package com.a10.mejabelajar.dashboard.admin.service;

import com.a10.mejabelajar.auth.model.AdminRegistrationToken;
import com.a10.mejabelajar.auth.model.AdminRegistrationTokenDto;

public interface AdminDashboardService {
    AdminRegistrationToken generateToken(AdminRegistrationTokenDto tokenDto);
}
