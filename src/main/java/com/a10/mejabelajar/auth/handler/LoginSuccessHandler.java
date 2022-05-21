package com.a10.mejabelajar.auth.handler;

import com.a10.mejabelajar.auth.model.Role;
import com.a10.mejabelajar.auth.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        var user = (User) authentication.getPrincipal();

        String redirectURL = request.getContextPath();

        if(user.getRole() == Role.ADMIN){
            redirectURL = "/dashboard/admin";
        }
        else if(user.getRole() == Role.STUDENT){
            redirectURL = "/dashboard/student";
        }
        else if(user.getRole() == Role.TEACHER){
            redirectURL = "/dashboard/teacher";
        }

        response.sendRedirect(redirectURL);

    }
}
