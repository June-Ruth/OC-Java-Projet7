package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	/**
	 * @see Logger
	 */
	private static final Logger LOGGER =
			LogManager.getLogger(HomeController.class);

	/**
	 * Home.
	 * @param model .
	 * @return home.
	 */
	@RequestMapping("/")
	public String home(final Model model) {
		LOGGER.info("getting home.");
		return "home";
	}

	/**
	 * Admin home.
	 * @param model .
	 * @return bid list.
	 */
	@RequestMapping("/admin/home")
	public String adminHome(final Model model) {
		LOGGER.info("redirect to bid list");
		return "redirect:/bidList/list";
	}
}
