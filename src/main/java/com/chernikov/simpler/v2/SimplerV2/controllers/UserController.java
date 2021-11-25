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

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
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
		userRepository.deleteById(userId);
		
		return "redirect:/getAll";
	}
	
	// returns a view for user updating
	@GetMapping("/{id}/update")
	public String updateUser(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("title", "Update user");
		model.addAttribute("user", userRepository.findById(id));
		return "updateUser";
	}
	
	@PostMapping("/{id}/update")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "redirect:/{id}/update";
		}
		User userObject = userRepository.findById(id).get();
        userObject = user;
        userRepository.save(userObject);
        return "redirect:/getAll";
    }
	
	// processes updating of user
//	@PostMapping("/update")
//	public String updateUserPageProcessing(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
//		
//		boolean existsById = userRepository.existsById(user.getId());
//		
//		model.addAttribute("user", user);
//		
//		if (bindingResult.hasErrors()) {
//			return "updateUser";
//		}
//		
//		if (existsById) {
//			User userToBeUpdated = userRepository.findById(user.getId()).get();
//			userToBeUpdated = user;
//			userRepository.save(userToBeUpdated);
//			return "redirect:/getAll";
//		} else {
//			model.addAttribute("existsById", existsById);
//			model.addAttribute("error", "The user with id = " + user.getId() + " doesn't exist");
//			return "redirect:/updateUser";
//		}
//	}
	
	
	// returns a view with all users
	@GetMapping(path = "/getAll")
	public String getAllUsers(Model model) {
		Iterable<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		model.addAttribute("title", "All the users");
		
		return "allUsers";
	}
}
