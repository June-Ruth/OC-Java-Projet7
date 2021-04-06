package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.nnk.springboot.constants.ErrorMessage.TOO_MUCH_CHARACTERS;
import static com.nnk.springboot.constants.Number.ONE_HUNDRED_TWENTY_FIVE;

@Entity
@Table(name = "user")
public class User {
    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    /**
     * Username.
     */
    @NotBlank(message = "Username is mandatory")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String username;
    /**
     * Password.
     */
    @NotBlank(message = "Password is mandatory")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String password;
    /**
     * Full name.
     */
    @NotBlank(message = "FullName is mandatory")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String fullname;
    /**
     * Role.
     */
    @NotBlank(message = "Role is mandatory")
    @Size(max = ONE_HUNDRED_TWENTY_FIVE, message = TOO_MUCH_CHARACTERS)
    private String role;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
