package com.nnk.springboot.configuration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * For demo version only.
 */
@Component
public class SetUpDataLoader
        implements ApplicationListener<ContextRefreshedEvent> {
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
     * @see DataSource
     */
    private DataSource dataSource;

    /**
     * Public constructor.
     * @param pUserService .
     * @param pPasswordEncoder .
     */
    public SetUpDataLoader(final UserService pUserService,
                           final PasswordEncoder pPasswordEncoder,
                           final DataSource pDataSource) {
        userService = pUserService;
        passwordEncoder = pPasswordEncoder;
        dataSource = pDataSource;
    }

    /**
     * Create default user for demo.
     * @param event .
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!alreadySetup) {
            try (Connection conn = dataSource.getConnection()) {
                ScriptUtils.executeSqlScript(conn, new ClassPathResource("scripts/data-demo.sql"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            User user = new User("user",
                    passwordEncoder.encode("Password1$"),
                    "User Test", "ROLE_USER");
            User admin = new User("admin",
                    passwordEncoder.encode("Password1$"),
                    "Admin Test", "ROLE_ADMIN");
            if (!userService.checkUserByUsername(user.getUsername())) {
                userService.saveUser(user);
            }
            if (!userService.checkUserByUsername(admin.getUsername())) {
                userService.saveUser(admin);
            }
            alreadySetup = true;
        }
    }
}
