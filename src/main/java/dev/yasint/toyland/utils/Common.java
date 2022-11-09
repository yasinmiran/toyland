package dev.yasint.toyland.utils;

import dev.yasint.toyland.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Common {

    public static UserDetails getUserDetailsFromContext() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

}
