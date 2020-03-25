package cn.tesu.store.mapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.store.entiy.Address;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.mapper.UserMapper;

public class AddressTest {
	
	private AbstractApplicationContext ac;
	
	private AddressMapper addressMapper;
	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("spring-dao.xml");
		addressMapper=ac.getBean("addressMapper",AddressMapper.class);
		
	}
	
	@Test
	public void test01() {
		Address address = new Address();
		address.setUid(10);
		Integer rows = addressMapper.insert(address);
		System.out.println(rows);
	}
	
}
