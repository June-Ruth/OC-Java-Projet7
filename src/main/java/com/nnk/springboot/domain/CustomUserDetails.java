package com.nnk.springboot.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    /**
     * Username.
     */
    private String username;
    /**
     * Password.
     */
    private String password;
    /**
     * Authorities.
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Public constructor.
     * @param pUsername .
     * @param pPassword .
     * @param pAuthorities .
     */
    public CustomUserDetails(final String pUsername,
                             final String pPassword,
                             final Collection<? extends GrantedAuthority> pAuthorities) {
        username = pUsername;
        password = pPassword;
        authorities = pAuthorities;
    }

    /**
     * Private empty constructor.
     */
    private CustomUserDetails() {

    }

    /**
     * Get Authorities.
     * @return authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Get password.
     * @return password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Get username.
     * @return username.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Account non expired if true.
     * @return true if non expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Account non locked.
     * @return true if non locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Credential non expired.
     * @return true if non expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Enabled.
     * @return true if enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
