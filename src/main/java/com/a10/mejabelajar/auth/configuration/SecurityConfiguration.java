package com.a10.mejabelajar.auth.configuration;

import com.a10.mejabelajar.auth.handler.LoginSuccessHandler;
import com.a10.mejabelajar.auth.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler();

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/admin/logs").authenticated()
                .antMatchers("/dashboard/admin/**").hasAnyAuthority("ADMIN")
                .antMatchers("/dashboard/student/**").hasAnyAuthority("STUDENT")
                .antMatchers("/dashboard/teacher/**").hasAnyAuthority("TEACHER")
                .antMatchers("/signup").permitAll()
                .antMatchers(
                        "/course/create",
                        "/course/update/**",
                        "/course/delete/**",
                        "/course/information/**",
                        "/admin/form-log").hasAnyAuthority("TEACHER")
                .antMatchers("/admin/**/bayar").hasAnyAuthority("STUDENT")
                .antMatchers("/admin/**/verifikasi",
                        "/admin/user-activation").hasAnyAuthority("ADMIN")
                .and().formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .failureUrl("/login?error=true")
                .and().logout()
                .logoutSuccessUrl("/login").permitAll();
        http.csrf().disable();
    }
}
