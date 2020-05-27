/**
 * 
 */
package com.user.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.constant.UserStatusConstant;
import com.user.entity.RoleEntity;
import com.user.entity.UserEntity;
import com.user.repository.RoleRepository;
import com.user.repository.UserRepository;

/**
 * @author yuvaraj
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<UserEntity> userOpt = userRepo.findByNameAndStatus(username, UserStatusConstant.ACTIVE);
		if (userOpt.isPresent()) {
			UserEntity userEntity = userOpt.get();

			Set<GrantedAuthority> authorities = new HashSet<>();

			Optional<RoleEntity> roles = roleRepo.findById(userEntity.getRole());
			if (roles.isPresent()) {
				authorities.add(new SimpleGrantedAuthority(roles.get().getName()));
			}

			return new User(userEntity.getName(), userEntity.getPassword(), authorities);
		} else {
			throw new UsernameNotFoundException(username);
		}

	}

}
