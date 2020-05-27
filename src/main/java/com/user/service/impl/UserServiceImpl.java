/**
 * 
 */
package com.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.bo.UserBo;
import com.user.constant.UserStatusConstant;
import com.user.dto.UserDto;
import com.user.entity.UserEntity;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import com.user.util.UserUtil;

/**
 * @author yuvaraj
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserUtil userUtil;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto saveUser(UserBo userBo) {
		UserEntity userEntity = new UserEntity();
		userEntity.setName(userBo.getUserName());
		userEntity.setPassword(bCryptPasswordEncoder.encode(userBo.getPassword()));
		userEntity.setStatus(UserStatusConstant.IN_ACTIVE);
		userEntity.setRole(1l);
		userRepo.save(userEntity);

		return userDto(userEntity);
	}

	@Override
	public UserDto updateUser(UserBo userBo) {
		Optional<UserEntity> userOpt = userRepo.findByName(userBo.getUserName());
		if (userOpt.isPresent()) {
			UserEntity userEntity = userOpt.get();
			userEntity.setName(userBo.getUserName());
			userEntity.setPassword(bCryptPasswordEncoder.encode(userBo.getPassword()));
			userRepo.save(userEntity);
			return userDto(userEntity);
		}
		return new UserDto();
	}

	@Override
	public boolean removeUser(UserBo userBo) {
		Optional<UserEntity> userOpt = userRepo.findByName(userBo.getUserName());
		if (userOpt.isPresent()) {
			UserEntity userEntity = userOpt.get();
			userRepo.delete(userEntity);
		}
		return true;
	}

	private UserDto userDto(UserEntity userEntity) {
		UserDto userDto = new UserDto();
		userDto.setRole(userUtil.getRole(userEntity.getRole()));
		userDto.setStatus(userEntity.getStatus());
		userDto.setUserName(userEntity.getName());
		return userDto;
	}

	@Override
	public UserDto loginUser(UserBo userBo) {
		Optional<UserEntity> userOpt = userRepo.findByNameAndStatus(userBo.getUserName(), UserStatusConstant.ACTIVE);

		UserDto userDto = new UserDto();
		if (userOpt.isPresent()) {
			UserEntity userEntity = userOpt.get();

			userEntity.setLoginStatus(UserStatusConstant.ACTIVE);
			userRepo.save(userEntity);

			userDto.setUserName(userEntity.getName());
			userDto.setRole(userUtil.getRole(userEntity.getRole()));
			userDto.setStatus(userEntity.getStatus());
		}
		return userDto;
	}

	@Override
	public boolean logoutUser(String name) {
		Optional<UserEntity> userOpt = userRepo.findByNameAndLoginStatus(name, UserStatusConstant.ACTIVE);
		if (userOpt.isPresent()) {
			UserEntity userEntity = userOpt.get();

			userEntity.setLoginStatus(UserStatusConstant.IN_ACTIVE);
			userRepo.save(userEntity);
		}

		return userOpt.isPresent();
	}

}
