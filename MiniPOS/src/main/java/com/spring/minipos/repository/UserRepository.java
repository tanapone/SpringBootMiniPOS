package com.spring.minipos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.minipos.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.id = ?1")
	User findUserById(long id);
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	User findUserByUser(String username);
	
	@Query("SELECT u FROM User u WHERE u.username =?1 AND u.password =?2")
	User checkLogin(String username,String password);
	
	@Query("SELECT u FROM User u WHERE u.authKey =?1")
	User checkAuthKey(String authKey);

}
