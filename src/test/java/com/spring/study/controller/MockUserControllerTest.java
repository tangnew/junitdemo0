/**
 * 
 */
package com.spring.study.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * @author Ma.Yan
 *
 *         Create Time: May 24, 2018 4:25:56 PM
 */
public class MockUserControllerTest {

	@Test
	public void mockList() {
		List list = mock(List.class);
		list.add(1);
		list.clear();
		// 验证add(1)和clear()行为是否发生
		verify(list).add(1);
		verify(list).clear();
	}
	
	  @Test
	    public void when_thenReturn(){
	        //mock一个Iterator类
	        Iterator iterator = mock(Iterator.class);
	        //预设当iterator调用next()时第一次返回hello，第n次都返回world
	        when(iterator.next()).thenReturn("hello").thenReturn("world");
	        //使用mock的对象
	        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
	        //验证结果
	        assertEquals("hello world world",result);
	    }
	  
	  
	  @Test(expected = IOException.class)
	    public void when_thenThrow() throws IOException {
	        OutputStream outputStream = mock(OutputStream.class);
	        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
	        //预设当流关闭时抛出异常
	        doThrow(new IOException()).when(outputStream).close();
	        outputStream.close();
	    }
	  
	  @Test
	    public void returnsSmartNullsTest() {
	        List mock = mock(List.class, RETURNS_SMART_NULLS);//RETURNS_DEEP_STUBS
	        System.out.println(mock.get(0));
	        
	        //使用RETURNS_SMART_NULLS参数创建的mock对象，不会抛出NullPointerException异常。另外控制台窗口会提示信息“SmartNull returned by unstubbed get() method on mock”
	        System.out.println(mock.toArray().length);
	    }
}