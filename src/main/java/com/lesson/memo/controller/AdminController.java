package com.lesson.memo.controller;

import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lesson.memo.model.Admin;
import com.lesson.memo.repository.AdminRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	public AdminController(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("admin", new Admin());
		return "signup";
	}

	@PostMapping("/signup")
	public String signup(@Valid Admin admin, BindingResult result) {
		if (result.hasErrors()) {
			return "signup";
		}

		String encodedPassword = passwordEncoder.encode(admin.getPassword());
		admin.setPassword(encodedPassword);

		adminRepository.save(admin);

		return "redirect:/admin/signin";
	}

	@GetMapping("/signin")
	public String showSigninForm() {
		return "signin";
	}

}