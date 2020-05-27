/**
 * 
 */
package com.user.service;

import com.user.bo.UserBo;
import com.user.dto.UserDto;

/**
 * @author yuvaraj
 *
 */
public interface UserService {

	UserDto saveUser(UserBo userBo);

	UserDto updateUser(UserBo userBo);

	boolean removeUser(UserBo userBo);

	UserDto loginUser(UserBo userBo);

	boolean logoutUser(String name);

}
