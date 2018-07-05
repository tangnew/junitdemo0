/**
 * 
 */
package com.spring.study;

import java.io.IOException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ma.Yan
 *
 * Create Time: May 24, 2018 4:26:34 PM
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=true)
@ContextConfiguration(locations = { "classpath:/application*.xml", "classpath:/spring*.xml", "classpath:/log4j.xml" })
public class SpringJunitBaseSupport {

	@Autowired
	protected WebApplicationContext wac;

	protected MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	protected ObjectMapper objectMapper = new ObjectMapper();

	protected String writeObejctToJsonValue(Object data) {
		try {
			return objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    
	protected  <T> T writeJsonValueToObject(String data,  Class<T> clz) {
		try {
			return objectMapper.readValue(data, clz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
