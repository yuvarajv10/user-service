/**
 * 
 */
package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.UserEntity;

/**
 * @author yuvaraj
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByName(String userName);

	Optional<UserEntity> findByNameAndStatus(String name, int status);

	Optional<UserEntity> findByNameAndLoginStatus(String name, int loginStatus);

}
