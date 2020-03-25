package cn.tedu.store.mapper;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entiy.User;

public interface UserMapper {
		Integer insert(User user);
		
		User findUserByUsername(String username);
		
		User findUserById(Integer id);
		
		Integer updataPassword(@Param("id")Integer id, @Param("password")String password);
		
		Integer updateInfo(User user);
		
		Integer updateAvatart(@Param("id")Integer id,@Param("avatart") String avatart);
}
