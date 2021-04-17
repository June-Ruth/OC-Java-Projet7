package com.nnk.springboot.services;

import com.nnk.springboot.constants.ErrorMessage;
import com.nnk.springboot.domain.CustomUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * @see UserRepository
     */
    private UserRepository userRepository;

    /**
     * Public constructor.
     * @param pUserRepository .
     */
    public UserDetailsServiceImpl(final UserRepository pUserRepository) {
        userRepository = pUserRepository;
    }

    /**
     * Define how username is defined.
     * @param email as username.
     * @return User.
     * @throws UsernameNotFoundException .
     */
    @Override
    public UserDetails loadUserByUsername(final String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        ErrorMessage.USERNAME_NOT_FOUND));

        return new CustomUserDetails(user.getUsername(), user.getPassword(), getAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
