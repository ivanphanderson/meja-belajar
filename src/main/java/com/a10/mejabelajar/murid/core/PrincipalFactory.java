package com.a10.mejabelajar.murid.core;

import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalFactory {

    public Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
