package cn.tedu.store.service;

import cn.tedu.store.entiy.User;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordNotMacthException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.PasswordFormatException;
import cn.tedu.store.service.ex.UserNotFindException;
import cn.tedu.store.service.ex.UsernameConfictException;

public interface IUserService {
	User reg(User user) throws UsernameConfictException,InsertDataException;
	
	User login(String username,String password) throws UserNotFindException,PasswordNotMacthException,PasswordFormatException,UpdateDataException;
	
	void changePassword(Integer id,String oldPassword,String newPassword)throws PasswordNotMacthException,PasswordFormatException,UpdateDataException;
	
	void changeInfo(User user)throws UpdateDataException,UserNotFindException;
	
	void changeAvatar(Integer id,String avatar) throws UserNotFindException,UpdateDataException;
	
	User findUserById(Integer id);
}
