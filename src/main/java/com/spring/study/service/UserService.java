/**
 * 
 */
package com.spring.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.study.mapper.UserMapper;
import com.spring.study.model.User;

/**
 * @author Ma.Yan
 *
 * Create Time: May 24, 2018 5:48:12 PM
 */
@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User getUserById(int userId) {
		return userMapper.getUserById(userId);
	}
	
	@Transactional
	public User crateUser(User user) {
		int i = userMapper.insertUser(user);
		return user;
	}
}
