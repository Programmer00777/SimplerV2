package com.chernikov.simpler.v2.SimplerV2.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chernikov.simpler.v2.SimplerV2.models.User;
import com.chernikov.simpler.v2.SimplerV2.repositories.UserRepository;

/**
 * Controller for the {@link com.chernikov.simpler.v2.SimplerV2.models.User} entity.
 *
 * @author sergeychernikov
 * @version 2.0
 */

@Controller
public class UserController {

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// return a lobby page
	@GetMapping(value = "/")
	public String indexPage(Model model) {
		model.addAttribute("title", "Lobby page");
		
		return "index";
	}
	
	// return a view for adding a new user
	@GetMapping(value = "/add")
	public String addUserPage(User user, Model model) {
		model.addAttribute("title", "Add user");
		
		return "addUser";
	}

	// processes entered data into the form to add a new user
	@PostMapping(value = "/add")
	public String addUserPageProcessing(@ModelAttribute @Valid User user, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "addUser";
		}
		
		userRepository.save(user);
		
		return "redirect:/getAll";
	}
	
	// returns a view for user deleting
	@GetMapping("/delete")
	public String deleteUserPage(Model model) {
		model.addAttribute("title", "Delete user");
		return "deleteUser";
	}
	
	// processes entered id into the form to remove user from the database
	@PostMapping("/delete")
	public String deleteUserPageProcessing(@RequestParam Integer userId) {
		
		if (userRepository.existsById(userId)) {
			userRepository.deleteById(userId);
			return "redirect:/getAll";
		} else {
			return "redirect:/delete";
		}
	}
	
	// returns a view for user updating
	@GetMapping("/{id}/update")
	public String updateUser(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("title", "Update user");
		model.addAttribute("user", userRepository.findById(id));
		return "updateUser";
	}
	
	@PostMapping("/{id}/update")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult, @PathVariable("id") Integer id) {

		if (bindingResult.hasErrors()) {
			return "redirect:/{id}/update";
		}

        userRepository.save(user);
        return "redirect:/getAll";
    }
	
	// returns a view with all users
	@GetMapping("/getAll")
	public String getAllUsersPage(Model model) {

		Iterable<User> users = userRepository.findAll();
		model.addAttribute("users", users);

		return "newAllUsers";
	}

}
