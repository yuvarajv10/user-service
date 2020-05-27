/**
 * 
 */
package com.user.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.user.entity.RoleEntity;
import com.user.repository.RoleRepository;

/**
 * @author yuvaraj
 *
 */
@Component
public class UserUtil {

	@Autowired
	RoleRepository roleRepo;

	public String getRole(Long role) {
		Optional<RoleEntity> roleOpt = roleRepo.findById(role);
		if (roleOpt.isPresent()) {
			return roleOpt.get().getName();
		}
		return "";
	}

}
