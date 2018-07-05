/**
 * 
 */
package com.spring.study.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.spring.study.model.User;

/**
 * @author Ma.Yan
 *
 * Create Time: May 24, 2018 5:35:16 PM
 */
public interface UserMapper {
	
	  @Select("SELECT * FROM user WHERE id = #{userId}")
	  User getUserById(@Param("userId") int userId);

	/**
	 * @param user
	 * @return
	 */
	@Insert(value="insert into user(name, gender, account, password, phone, create_time, update_time, description) values"
			+ " (#{name}, #{gender}, #{account}, #{password}, #{phone}, #{createTime}, #{updateTime}, #{description})")  
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertUser(User user);
}
