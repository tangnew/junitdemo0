/**
 * 
 */
package com.spring.study.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Ma.Yan
 *
 *         Create Time: May 25, 2018 11:14:22 AM
 */
public class MockitoExample2Test {

	@Mock
	private List mockList;

	public MockitoExample2Test() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shorthand() {
		mockList.add(1);
		verify(mockList).add(1);
	}
	/*
	 * @Test public void with_arguments(){ Comparable comparable =
	 * mock(Comparable.class); //预设根据不同的参数返回不同的结果
	 * when(comparable.compareTo("Test")).thenReturn(1);
	 * when(comparable.compareTo("Omg")).thenReturn(2); assertEquals(1,
	 * comparable.compareTo("Test")); assertEquals(2, comparable.compareTo("Omg"));
	 * //对于没有预设的情况会返回默认值 assertEquals(0, comparable.compareTo("Not stub")); }
	 * 
	 * @Test public void with_unspecified_arguments(){ List list = mock(List.class);
	 * //匹配任意参数 when(list.get(anyInt())).thenReturn(1);
	 * when(list.contains(argThat(new IsValid()))).thenReturn(true); assertEquals(1,
	 * list.get(1)); assertEquals(1, list.get(999)); assertTrue(list.contains(1));
	 * assertTrue(!list.contains(3)); }
	 * 
	 * private class IsValid extends ArgumentMatcher<List>{
	 * 
	 * @Override public boolean matches(Object o) { return o == 1 || o == 2; } }
	 */

	/**
	 * 用spy监控真实对象 Mock不是真实的对象，它只是用类型的class创建了一个虚拟对象，并可以设置对象行为 Spy是一个真实的对象，但它可以设置对象行为
	 * InjectMocks创建这个类的对象并自动将标记@Mock、@Spy等注解的属性值注入到这个中
	 */
	@Test
	public void testSpy() {
		List list = new ArrayList();
		List spy = Mockito.spy(list);
		// 下面预设的spy.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
		// when(spy.get(0)).thenReturn(3);

		// 使用doReturn-when可以避免when-thenReturn调用真实对象api
		doReturn(999).when(spy).get(999);
		// 预设size()期望值
		when(spy.size()).thenReturn(100);
		// 调用真实对象的api
		spy.add(1);
		spy.add(2);
		assertEquals(100, spy.size());
		// assertEquals(1, spy.get(0));
		// assertEquals(2, spy.get(1));
		// verify(spy).add(1);
		// verify(spy).add(2);
		assertEquals(999, spy.get(999));
		// spy.get(2);
	}

	/**
	 * 真实的部分mock
	 */

	@Test
	public void real_partial_mock() {
		// 通过spy来调用真实的api
		List list = spy(new ArrayList());
		assertEquals(0, list.size());
		A a = mock(A.class);
		// 通过thenCallRealMethod来调用真实的api
		when(a.doSomething(1)).thenCallRealMethod();
		assertEquals(999, a.doSomething(999));

		// 重置mock，清除所有的互动和预设
		reset(list);
	}

	class A {
		public int doSomething(int i) {
			return i;
		}
	}
	
	@Test(expected = RuntimeException.class)
    public void consecutive_calls(){
        //模拟连续调用返回期望值，如果分开，则只有最后一个有效
        when(mockList.get(0)).thenReturn(0);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(0)).thenReturn(2);
        when(mockList.get(1)).thenReturn(0).thenReturn(1).thenThrow(new RuntimeException());
        assertEquals(2,mockList.get(0));
        assertEquals(2,mockList.get(0));
        assertEquals(0,mockList.get(1));
        assertEquals(1,mockList.get(1));
        //第三次或更多调用都会抛出异常
        mockList.get(1);
    }
	
	@Test
    public void verifying_number_of_invocations(){
        List list = mock(List.class);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        //验证是否被调用一次，等效于下面的times(1)
        verify(list).add(1);
        verify(list,times(1)).add(1);
        //验证是否被调用2次
        verify(list,times(2)).add(2);
        //验证是否被调用3次
        verify(list,times(3)).add(3);
        //验证是否从未被调用过
        verify(list,never()).add(4);
        //验证至少调用一次
        verify(list,atLeastOnce()).add(1);
        //验证至少调用2次
        verify(list,atLeast(2)).add(2);
        //验证至多调用3次
        verify(list,atMost(3)).add(3);
    }
	
	//2.16 验证执行顺序
	@Test
    public void verification_in_order(){
        List list = mock(List.class);
        List list2 = mock(List.class);
        list.add(1);
        list2.add("hello");
        list.add(2);
        list2.add("world");
        //将需要排序的mock对象放入InOrder
        InOrder inOrder = Mockito.inOrder(list,list2);
        //下面的代码不能颠倒顺序，验证执行顺序
        inOrder.verify(list).add(1);
        inOrder.verify(list2).add("hello");
        inOrder.verify(list).add(2);
        inOrder.verify(list2).add("world");
    }
}
