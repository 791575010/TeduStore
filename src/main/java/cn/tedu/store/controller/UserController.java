package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.tedu.store.entiy.ResponseResult;
import cn.tedu.store.entiy.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.TextValidator;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	private static final int MAX_AVATAR_SIZE = 1*1024*1024;
	
	private static final List<String> AVATAR_TYPE_WHITE_LISE = new ArrayList<String>();
	
	@PostConstruct
	public void init() {
		AVATAR_TYPE_WHITE_LISE.add("image/jpeg");
		AVATAR_TYPE_WHITE_LISE.add("image/png");
	}
	
	@Autowired
	private IUserService userService;

	@RequestMapping(value="/handle_reg.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleReg(User user){
		if(!TextValidator.checkUsername(user.getUsername())) {
			return new ResponseResult<Void>(301,"用户名不合法");
		}
		if(!TextValidator.checkPassword(user.getPassword())) {
			return new ResponseResult<Void>(302,"密码不合法");
		}
		userService.reg(user);
		return new ResponseResult<Void>();
	}
	
	@RequestMapping(value="/handle_login.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleLogin(String username,String password,HttpSession session){
		if(!TextValidator.checkUsername(username)) {
			return new ResponseResult<Void>(301,"用户名不合法");
		}
		if(!TextValidator.checkPassword(password)) {
			return new ResponseResult<Void>(302,"密码不合法");
		}
		User user = userService.login(username, password);
		session.setAttribute("uid", user.getId());
		session.setAttribute("uname", user.getUsername());
		return new ResponseResult<Void>();
	}
	
	@RequestMapping(value="/change_password.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> changePassword(@RequestParam("old_password")String oldPassword, @RequestParam("new_password")String newPassword,HttpSession session){
		if(!TextValidator.checkUsername(oldPassword)) {
			return new ResponseResult<Void>(302,"旧的密码不合法");
		}
		if(!TextValidator.checkPassword(newPassword)) {
			return new ResponseResult<Void>(302,"新的密码不合法");
		}
		Integer id = Integer.parseInt(session.getAttribute("uid").toString());
		userService.changePassword(id, oldPassword, newPassword);
		return new ResponseResult<Void>();
	}
	
	@RequestMapping(value="/change_info.do" ,method=RequestMethod.GET)
	@ResponseBody
	public ResponseResult<Void> changeInfo(User user,HttpSession session){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		user.setId(Integer.parseInt(session.getAttribute("uid").toString()));
		userService.changeInfo(user);
		return rr;
	}
	
	@RequestMapping(value="/upload.do" ,method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> upload(HttpServletRequest request,HttpSession session,@RequestParam("file") CommonsMultipartFile file){
		if(file.isEmpty()) {
			return new ResponseResult<String>();
		}
		
		long fileSize = file.getSize();
		if(fileSize>MAX_AVATAR_SIZE) {
			return new ResponseResult<String>();
		}
		
		String contentType = file.getContentType();
		if(!AVATAR_TYPE_WHITE_LISE.contains(contentType)) {
			return new ResponseResult<String>();
		}
		Integer id = getUserId(session);
		String originalFileName = file.getOriginalFilename();
		int beaginIndex = originalFileName.lastIndexOf(".");
		String suffix = originalFileName.substring(beaginIndex);
		String fileNmae = getFileName(id)+ suffix;
		String uploadDirName = "upload";
		String pathname = request.getServletContext().getRealPath(uploadDirName);
		File parentDir = new File(pathname );
		if(!parentDir.exists()) {
			parentDir.mkdirs();
		}
		File dest = new File(parentDir, fileNmae);
		try {
			file.transferTo(dest );
		} catch (IllegalStateException e) {
			return new ResponseResult<String>(601,"读取文件失败，文件位置发生变动");
		} catch (IOException e) {
			return new ResponseResult<String>(602,"文件读取失败，文件可能已被移动删除网络连接中断");
		}
		String avatar = uploadDirName+"/"+fileNmae;
		userService.changeAvatar(id, avatar );
		ResponseResult<String> rr = new ResponseResult<String>();
		rr.setData(avatar);
		return rr;
	}
	
	private String getFileName(Integer id) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return id+"-"+sdf.format(date);
	}

	@RequestMapping(value="/info.do" ,method=RequestMethod.GET)
	@ResponseBody
	public ResponseResult<User> info(HttpSession session){
		ResponseResult<User> rr = new ResponseResult<User>();
		Integer id = Integer.parseInt(session.getAttribute("uid").toString());
		User user = userService.findUserById(id);
		rr.setData(user);
		return rr;
	}
}


