/**
 * 
 */
package com.spring.study.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.study.model.User;
import com.spring.study.service.UserService;

/**
 * @author Ma.Yan
 *
 *         Create Time: May 24, 2018 4:12:38 PM
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public User createUser(@RequestBody User user) {
		System.out.println(user);
		Date curTime = new Date();
		user.setCreateTime(System.currentTimeMillis());
		user.setUpdateTime(System.currentTimeMillis());
		userService.crateUser(user);
		return user;
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public User createUser(int userId) {
		return userService.getUserById(userId);
	}
}
