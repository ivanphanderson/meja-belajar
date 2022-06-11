package com.a10.mejabelajar.auth.handler;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.User;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        var user = (User) authentication.getPrincipal();

        String redirectUrl = request.getContextPath();

        if (user.getRole() == Role.ADMIN) {
            redirectUrl = "/dashboard/admin/";
        } else if (user.getRole() == Role.STUDENT) {
            redirectUrl = "/dashboard/student/";
        } else if (user.getRole() == Role.TEACHER) {
            redirectUrl = "/dashboard/teacher/";
        }

        response.sendRedirect(redirectUrl);

    }
}
