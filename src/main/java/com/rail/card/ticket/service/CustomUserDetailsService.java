package com.rail.card.ticket.service;

import com.rail.card.ticket.constant.ApplicationEnum;
import com.rail.card.ticket.model.User;
import com.rail.card.ticket.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findFirstByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found: " + email);
        }

        // ===== mapping INT → ENUM =====
        int roleOrdinal = user.getRole().getRoleName().ordinal(); // 0 / 1
        ApplicationEnum.Group group =
                ApplicationEnum.Group.values()[roleOrdinal];

        // ===== ENUM → ROLE string =====
        GrantedAuthority authority =
                new SimpleGrantedAuthority(group.toRole());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(authority)
                .accountLocked(false)
                .disabled(false)
                .build();
    }
}
