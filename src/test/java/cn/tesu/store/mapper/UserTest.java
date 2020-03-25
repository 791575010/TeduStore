package cn.tesu.store.mapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entiy.User;
import cn.tedu.store.mapper.UserMapper;

public class UserTest {
	
	private AbstractApplicationContext ac;
	
	private UserMapper userMapper;
	
	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("spring-dao.xml");
		userMapper = ac.getBean("userMapper",UserMapper.class);
		
	}
	
	@Test
	public void insert() {
		User user = new User();
		user.setUsername("root*");
		user.setPassword("1234");
		user.setSalt("456");
		user.setGender(1);
		Integer rows = userMapper.insert(user);
		System.out.println(rows);
		System.out.println(user);
	}
	
	@Test
	public void update() {
		
		System.out.println(userMapper.updateAvatart(21, "1222222222"));
	}
	
	@Test
	public void updateInfo() {
		User user = new  User();
		user.setGender(1);
		user.setEmail("791575@");
		user.setPhone("1509472");
		Integer i = userMapper.updateInfo(user);
		
		System.out.println("rows="+i);
	}
	
	@Test
	public void findByName() {
		User user = userMapper.findUserById(21);
		System.out.println(user);
	}
}
