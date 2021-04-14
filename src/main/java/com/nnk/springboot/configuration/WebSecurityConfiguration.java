package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * @see UserDetailsService
     */
    private final UserDetailsService userDetailsService;

    /**
     * Autowired constructor.
     * @param pUserDetailsService .
     */
    public WebSecurityConfiguration(
            @Qualifier("userDetailsServiceImpl") final UserDetailsService pUserDetailsService) {
        userDetailsService = pUserDetailsService;
    }

    /**
     * Define authentication with userDetailsService.
     * @param auth .
     * @throws Exception .
     */
    @Override
    public void configure(
            final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * Configure Spring Security Filter Chaine.
     * @param http .
     * @throws Exception .
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/bidList/list", true)
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }

    /**
     * Password Encoder.
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
