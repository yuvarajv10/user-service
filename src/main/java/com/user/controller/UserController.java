/**
 * 
 */
package com.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.user.bo.UserBo;
import com.user.component.UserComponent;
import com.user.dto.UserDto;

/**
 * User's related services.
 * 
 * @author yuvaraj
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserComponent userComponent;

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Add new user.
	 * 
	 * @param userBo
	 * @return
	 */
	@PostMapping(value = "/save")
	@ResponseBody
	public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserBo userBo) {
		return new ResponseEntity<>(userComponent.saveUser(userBo), HttpStatus.OK);
	}

	/**
	 * Update the user detail.
	 * 
	 * @param userBo
	 * @return
	 */
	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserBo userBo) {
		return new ResponseEntity<>(userComponent.updateUser(userBo), HttpStatus.OK);
	}

	/**
	 * Delete users detail.
	 * 
	 * @param userBo
	 * @return
	 */
	@DeleteMapping("/remove")
	public ResponseEntity<?> removeUser(@RequestBody @Valid UserBo userBo) {
		return new ResponseEntity<>(userComponent.removeUser(userBo), HttpStatus.OK);
	}

	/**
	 * Login by users detail.
	 * 
	 * @param userBo
	 * @return
	 */
	@PostMapping(value = "/login")
	@ResponseBody
	public ResponseEntity<?> loginUser(@RequestBody @Valid UserBo userBo) {

		UserDetails userDetails = userComponent.loadUserByUsername(userBo.getUserName());
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, userBo.getPassword(), null);

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		UserDto userDto = new UserDto();
		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			userDto = userComponent.loginUser(userBo);
		}

		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	/**
	 * Logout users details.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/logout")
	@ResponseBody
	public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				userComponent.logoutUser(auth.getName());
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Checking authentication.
	 * 
	 * @return
	 */
	@GetMapping(value = "/status")
	@ResponseBody
	public ResponseEntity<?> userStatus() {
		if (userComponent.checkLoginUser()) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
}
