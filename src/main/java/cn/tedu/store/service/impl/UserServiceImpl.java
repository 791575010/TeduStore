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
			throw new UsernameConfictException("�û����Ѵ���");
		}
	}
	
	public User login(String username, String password) throws UserNotFindException, PasswordNotMacthException {
		if(!TextValidator.checkUsername(username)) {
			throw new UsernameFormatException("�û�����ʽ����");
		}
		if(!TextValidator.checkPassword(password)) {
			throw new PasswordFormatException("�����ʽ����");
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
				throw new PasswordNotMacthException("�������");
			}
		}else {
			throw new UserNotFindException("�û���������");
		}
	}
	
	public void changeAvatar(Integer id, String avatar) throws UserNotFindException, UpdateDataException {
		if(findUserById(id)==null) {
			throw new UserNotFindException("�û�������");
		}
		
		updataAvatar(id, avatar);
		
	}
	
	public void changeInfo(User user) throws UpdateDataException,UserNotFindException{
		Integer uid = user.getId();
		if(uid==null) {
			throw new UpdateDataException("��¼�Ѿ����ڣ������µ�½");
		}
		
		User data = findUserById(uid);
		if(data==null) {
			throw new UserNotFindException("�û���Ϣ������");
		}
		
		user.setUsername(data.getUsername());
		
		updataInfo(user);
		
	}
	
	public void changePassword(Integer id, String oldPassword, String newPassword) throws PasswordNotMacthException,PasswordFormatException,UpdateDataException{
		if(!TextValidator.checkPassword(oldPassword)) {
			throw new PasswordFormatException("�ɵ������ʽ����");
		}
		if(!TextValidator.checkPassword(newPassword)) {
			throw new PasswordFormatException("�µ������ʽ����");
		}
		User user = findUserById(id);
		
		if(user!=null) {
			oldPassword = getEncrpytedPassword(oldPassword, user.getSalt());
			if(user.getPassword().equals(oldPassword)) {
				newPassword = getEncrpytedPassword(newPassword, user.getSalt());
				updataPassword(id, newPassword);
			}else {
				throw new PasswordNotMacthException("ԭ�������");
			}
		}else {
			throw new UserNotFindException("�û������ڻ��Ѿ���ɾ��");
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
			throw new InsertDataException("����ʧ��");
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
			throw new UpdateDataException("�޸����뷢��δ֪��������ϵ����Ա");
		}
	}

	private void updataInfo(User user) {
		Integer rows = userMapper.updateInfo(user);
		if(rows!=1) {
			throw new UpdateDataException("�޸��û�����ʱ����δ֪�쳣");
		}
	}

	private void updataAvatar(Integer id,String avatar) {
		Integer rows = userMapper.updateAvatart(id, avatar);
		if(rows!=1) {
			throw new UpdateDataException("�޸��û�����ʱ����δ֪�쳣");
		}
	} 
	
	
	
}
