/**
 * 
 */
package com.user.component;

import org.springframework.security.core.userdetails.UserDetails;

import com.user.bo.UserBo;
import com.user.dto.UserDto;

/**
 * @author yuvaraj
 *
 */
public interface UserComponent {
	
	UserDto saveUser(UserBo userBo);
	
	UserDto updateUser(UserBo userBo);
	
	boolean removeUser(UserBo userBo);
	
	UserDto loginUser(UserBo userBo);
	
	boolean logoutUser(String name);

	UserDetails loadUserByUsername(String userName);
	
	boolean checkLoginUser();

}
