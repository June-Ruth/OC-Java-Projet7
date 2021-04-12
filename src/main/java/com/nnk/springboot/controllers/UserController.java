package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    /**
     * @see Logger
     */
    private static final Logger LOGGER =
            LogManager.getLogger(UserController.class);
    /**
     * @see UserService
     */
    private UserService userService;

    /**
     * Autowired constructor.
     * @param pUserService .
     */
    public UserController(final UserService pUserService) {
        userService = pUserService;
    }

    /**
     * Get all the user.
     * @param model .
     * @return all user.
     */
    @RequestMapping("/user/list")
    public String home(final Model model) {
        List<User> result = userService.findAllUser();
        model.addAttribute("userList", result);
        LOGGER.info("Find all user, size = " + result.size());
        return "user/list";
    }

    /**
     * Prepare the form to add a user.
     * @param user .
     * @return the form.
     */
    @GetMapping("/user/add")
    public String addUserForm(final User user) {
        LOGGER.info("Show form to add user");
        return "user/add";
    }

    /**
     * Add a new user.
     * @param user to add.
     * @param result .
     * @param model .
     * @return the new list of user.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid final User user,
                           final BindingResult result,
                           final Model model) {
        LOGGER.info("Try to save new user : " + user);
        if (!result.hasErrors()) {
            User user1 = userService.saveUser(user);
            LOGGER.info("Save user : " + user1);
            return "redirect:/user/list";
        }
        LOGGER.error("Can't save following user, "
                + "must be invalid data :" + user);
        return "user/add";
    }

    /**
     * Get the form to update user.
     * @param id of the user to update.
     * @param model .
     * @return form.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
                                 final Model model) {
        LOGGER.info("Show the form to update user with id " + id);
        model.addAttribute("user", userService.findUserById(id));
        return "user/update";
    }

    /**
     * Update a user.
     * @param id of the user to update.
     * @param user with updated information.
     * @param result .
     * @param model .
     * @return list with updated user.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") final Integer id,
                              @Valid final User user,
                              final BindingResult result,
                              final Model model) {
        LOGGER.info("Try to update user with id " + id);
        if (!result.hasErrors()) {
            User user1 = userService.findUserById(id);
            user1.setFullname(user.getFullname());
            user1.setUsername(user.getUsername());
            user1.setPassword(user.getPassword());
            user1.setRole(user.getRole());
            userService.saveUser(user1);
            LOGGER.info("Success to update user " + user1);
            return "redirect:/user/list";
        }
        user.setId(id);
        LOGGER.error("Can't update following user, "
                + "must be invalid data " + user);
        return "user/update";
    }

    /**
     * Delete a user.
     * @param id of user to delete.
     * @param model .
     * @return the new list of user.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") final Integer id,
                              final Model model) {
        LOGGER.info("Try to delete user with id : " + id);
        User user = userService.findUserById(id);
        userService.deleteUser(user);
        return "redirect:/user/list";
    }
}
