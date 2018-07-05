/**
 * 
 */
package com.spring.study.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.spring.study.SpringJunitBaseSupport;
import com.spring.study.model.User;

/**
 * @author Ma.Yan
 *
 * Create Time: May 24, 2018 4:25:56 PM
 */
public class UserControllerTest extends SpringJunitBaseSupport {

	/**
	 * Test method for
	 * {@link com.spring.study.controller.UserController#createUser(com.spring.study.model.User)}.
	 */
	@Test
	public void testCreateUser00() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/create")).andExpect(status().isOk());
	}

	@Test
	@Transactional(transactionManager="transactionManager")
	@Rollback(value=true)
	public void testCreateUser01() throws Exception {
		User user = new User();
		user.setName("zs121");
		user.setAccount("zs1211");
		user.setPassword("zs11");
		user.setPhone("12");
		/*mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(writeObejctToJsonValue(user)))
				.andExpect(status().isOk()).andExpect(content().json(writeObejctToJsonValue(user)));*/
		
		MvcResult mr =	mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(writeObejctToJsonValue(user))).andReturn();
		System.out.println("create:"+mr.getResponse().getContentAsString());
		
		User aUser = writeJsonValueToObject(mr.getResponse().getContentAsString(), User.class);
	    mockMvc.perform(MockMvcRequestBuilders.get("/user/get?userId="+aUser.getId())).andReturn();
		System.out.println("get:"+mr.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetUser01() throws Exception {
		MvcResult mr =	mockMvc.perform(MockMvcRequestBuilders.get("/user/get?userId=1")).andReturn();
		System.out.println(mr.getResponse().getContentAsString());
	}
}
