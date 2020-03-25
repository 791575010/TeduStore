package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entiy.ResponseResult;
import cn.tedu.store.entiy.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMacthException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.PasswordFormatException;
import cn.tedu.store.service.ex.UserNotFindException;
import cn.tedu.store.service.ex.UsernameConfictException;
import cn.tedu.store.service.ex.UsernameFormatException;
import cn.tedu.store.util.TextValidator;
@Service("userService")
public class UserServiceImpl implements IUserService{
	@Autowired
	private UserMapper userMapper;
	public User reg(User user) throws UsernameConfictException, InsertDataException {
		User us = findUserByUsername(user.getUsername());
		if(us==null) {
			User result = insert(user);
			return result;
			
		}else {
			throw new UsernameConfictException("用户名已存在");
		}
	}
	
	public User login(String username, String password) throws UserNotFindException, PasswordNotMacthException {
		if(!TextValidator.checkUsername(username)) {
			throw new UsernameFormatException("用户名格式错误");
		}
		if(!TextValidator.checkPassword(password)) {
			throw new PasswordFormatException("密码格式错误");
		}
		User user = findUserByUsername(username);
		if(user!=null) {
			String salt = user.getSalt();
			String pass = getEncrpytedPassword(password, salt);
			if(pass.equals(user.getPassword())) {
				user.setPassword(null);
				user.setSalt(null);
				return user;
			}else {
				throw new PasswordNotMacthException("密码错误");
			}
		}else {
			throw new UserNotFindException("用户名不存在");
		}
	}
	
	public void changeAvatar(Integer id, String avatar) throws UserNotFindException, UpdateDataException {
		if(findUserById(id)==null) {
			throw new UserNotFindException("用户不存在");
		}
		
		updataAvatar(id, avatar);
		
	}
	
	public void changeInfo(User user) throws UpdateDataException,UserNotFindException{
		Integer uid = user.getId();
		if(uid==null) {
			throw new UpdateDataException("登录已经过期，请重新登陆");
		}
		
		User data = findUserById(uid);
		if(data==null) {
			throw new UserNotFindException("用户信息不存在");
		}
		
		user.setUsername(data.getUsername());
		
		updataInfo(user);
		
	}
	
	public void changePassword(Integer id, String oldPassword, String newPassword) throws PasswordNotMacthException,PasswordFormatException,UpdateDataException{
		if(!TextValidator.checkPassword(oldPassword)) {
			throw new PasswordFormatException("旧的密码格式错误");
		}
		if(!TextValidator.checkPassword(newPassword)) {
			throw new PasswordFormatException("新的密码格式错误");
		}
		User user = findUserById(id);
		
		if(user!=null) {
			oldPassword = getEncrpytedPassword(oldPassword, user.getSalt());
			if(user.getPassword().equals(oldPassword)) {
				newPassword = getEncrpytedPassword(newPassword, user.getSalt());
				updataPassword(id, newPassword);
			}else {
				throw new PasswordNotMacthException("原密码错误");
			}
		}else {
			throw new UserNotFindException("用户不存在或已经被删除");
		}
		
		
	}
	
	private User findUserByUsername(String username) {
		return userMapper.findUserByUsername(username);
	}
	
	private User insert(User user) {
		String salt = UUID.randomUUID().toString();
		user.setSalt(salt);
		Date date = new Date();
		user.setModifiedTime(date);
		user.setCreatedTime(date);
		user.setCreatedUser(user.getUsername());
		user.setModifiedUser(user.getUsername());
		user.setPassword(getEncrpytedPassword(user.getPassword(),user.getSalt()));
		Integer num = userMapper.insert(user);
		if(num==1) {
			return user;
		}else {
			throw new InsertDataException("插入失败");
		}
		
	}
	
	private String getEncrpytedPassword(String oidPassword,String salt) {
		byte[] bytes = (oidPassword+salt).getBytes();
		String passwprd = DigestUtils.md5DigestAsHex(bytes);
		return passwprd;
	}
	
	public User findUserById(Integer id) {
		return userMapper.findUserById(id);
	}
	
	private Integer updataPassword(Integer id, String password) throws UpdateDataException{
		
		Integer rows = userMapper.updataPassword(id, password);
		if(rows==1) {
			return rows;
		}else {
			throw new UpdateDataException("修改密码发生未知错误，请联系管理员");
		}
	}

	private void updataInfo(User user) {
		Integer rows = userMapper.updateInfo(user);
		if(rows!=1) {
			throw new UpdateDataException("修改用户数据时出现未知异常");
		}
	}

	private void updataAvatar(Integer id,String avatar) {
		Integer rows = userMapper.updateAvatart(id, avatar);
		if(rows!=1) {
			throw new UpdateDataException("修改用户数据时出现未知异常");
		}
	} 
	
	
	
}
