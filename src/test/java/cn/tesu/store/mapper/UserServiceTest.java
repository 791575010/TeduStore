package cn.tesu.store.mapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entiy.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.impl.UserServiceImpl;

public class UserServiceTest {
	
	private AbstractApplicationContext ac;
	
	private IUserService userService;
	public static void main(String[] args) {
		
	}
	
	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("spring-dao.xml","spring-service.xml");
		userService = ac.getBean("userService",UserServiceImpl.class);
		
		
	}
	@Test
	public void uPassword() {
		try {
			userService.changePassword(21, "dengxiangfei", "saodan");
		}catch (Exception e) {
			System.out.println(e.getMessage());		}
		
	}
	
	@Test
	public void uAvatar() {
		try {
			userService.changeAvatar(21, "abcdeefg");
		}catch (Exception e) {
			System.out.println(e.getMessage());		}
		
	}
	
	@Test
	public void insert() {
		User user = new User();
		user.setUsername("lisis");
		user.setPassword("1234");
		user.setGender(1);
		userService.reg(user);
	}
	@Test
	public void info() {
		User user = new User();
		user.setId(18);
		user.setUsername("lisis");
		user.setPassword("1234");
		user.setGender(1);
		try {
			userService.changeInfo(user);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	@Test
	public void login() {
		String username = "lisi";
		String password = "1234";
		User user = userService.login(username, password);
		System.out.println(user);
	}
	@Test
	public void findByName() {
		
	}
}
