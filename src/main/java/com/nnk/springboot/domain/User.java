package com.nnk.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.nnk.springboot.constants.ErrorMessage.FIELD_IS_MANDATORY;
import static com.nnk.springboot.constants.ErrorMessage.TOO_MUCH_CHARACTERS;
import static com.nnk.springboot.constants.Number.ONE_HUNDRED_TWENTY_FIVE;

@Entity
@Table(name = "user")
public class User {
    /**
     * Public constructor.
     * @param pUsername .
     * @param pPassword .
     * @param pFullname .
     * @param pRole .
     */
    public User(final String pUsername,
                final String pPassword,
                final String pFullname,
                final String pRole) {
        username = pUsername;
        password = pPassword;
        fullname = pFullname;
        role = pRole;
    }

    /**
     * Private empty constructor.
     */
    private User() { }

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * Username.
     */
    @NotBlank(message = FIELD_IS_MANDATORY)
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String username;
    /**
     * Password.
     */
    @NotBlank(message = FIELD_IS_MANDATORY)
    @Pattern(regexp = "(^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,125}$)",
            message = "Password must have at least 1 upper case,"
                    + "one digit and be between 8 and 125 characters long.")
    private String password;

    /**
     * Full name.
     */
    @NotBlank(message = FIELD_IS_MANDATORY)
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String fullname;
    /**
     * Role.
     */
    @NotBlank(message = FIELD_IS_MANDATORY)
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String role;

    /**
     * Getter.
     * @return id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter.
     * @param pId .
     */
    public void setId(final Integer pId) {
        id = pId;
    }

    /**
     * Getter.
     * @return username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter.
     * @param pUsername .
     */
    public void setUsername(final String pUsername) {
        username = pUsername;
    }

    /**
     * Getter.
     * @return password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter.
     * @param pPassword .
     */
    public void setPassword(final String pPassword) {
        password = pPassword;
    }

    /**
     * Getter.
     * @return fullname.
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Setter.
     * @param pFullname .
     */
    public void setFullname(final String pFullname) {
        fullname = pFullname;
    }

    /**
     * Getter.
     * @return role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter.
     * @param pRole .
     */
    public void setRole(final String pRole) {
        role = pRole;
    }
}
