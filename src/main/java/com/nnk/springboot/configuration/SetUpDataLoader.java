package com.nnk.springboot.configuration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetUpDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * App is already set up ?
     */
    private boolean alreadySetup = false;
    /**
     * @see UserService
     */
    private UserService userService;
    /**
     * @see PasswordEncoder
     */
    private PasswordEncoder passwordEncoder;

    /**
     * Public constructor.
     * @param pUserService .
     * @param pPasswordEncoder .
     */
    public SetUpDataLoader(final UserService pUserService,
                           final PasswordEncoder pPasswordEncoder) {
        userService = pUserService;
        passwordEncoder = pPasswordEncoder;
    }

    /**
     * NOT FOR PRODUCTION.
     * Create default user for demo.
     * @param event .
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!alreadySetup) {
            User user = new User("user", passwordEncoder.encode("password"), "User Test", "ROLE_USER");
            User admin = new User("admin", passwordEncoder.encode("password"), "Admin Test", "ROLE_ADMIN");
            userService.saveUser(user);
            userService.saveUser(admin);
            alreadySetup = true;
        }
    }
}
