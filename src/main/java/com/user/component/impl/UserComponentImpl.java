/**
 * 
 */
package com.user.component.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.user.bo.UserBo;
import com.user.component.UserComponent;
import com.user.dto.UserDto;
import com.user.service.UserService;

/**
 * @author yuvaraj
 *
 */
@Component
public class UserComponentImpl implements UserComponent {

	@Autowired
	UserDetailsService userDetailService;

	@Autowired
	UserService userServices;

	@Override
	public UserDto saveUser(UserBo userBo) {
		return userServices.saveUser(userBo);
	}

	@Override
	public UserDto updateUser(UserBo userBo) {
		return userServices.updateUser(userBo);
	}

	@Override
	public boolean removeUser(UserBo userBo) {
		return userServices.removeUser(userBo);
	}

	@Override
	public UserDto loginUser(UserBo userBo) {
		return userServices.loginUser(userBo);
	}

	@Override
	public boolean logoutUser(String name) {
		return userServices.logoutUser(name);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		return userDetailService.loadUserByUsername(userName);
	}

	@Override
	public boolean checkLoginUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				userDetailService.loadUserByUsername(auth.getName());
				return true;
			}
		}

		return false;
	}

}
