package cn.tedu.store.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.entiy.ResponseResult;
import cn.tedu.store.service.ex.InsertDataException;
import cn.tedu.store.service.ex.PasswordFormatException;
import cn.tedu.store.service.ex.PasswordNotMacthException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateDataException;
import cn.tedu.store.service.ex.UserNotFindException;
import cn.tedu.store.service.ex.UsernameConfictException;
import cn.tedu.store.service.ex.UsernameFormatException;

public abstract class BaseController {
	
	@ExceptionHandler(ServiceException.class)
	@ResponseBody
	public ResponseResult<Void> handleException(Exception e){
		if( e instanceof UsernameFormatException) {
			return new ResponseResult<Void>(301,e);
		}else if(e instanceof PasswordFormatException) {
			return new ResponseResult<Void>(302,e);
		}else if(e instanceof UsernameConfictException) {
			return new ResponseResult<Void>(401,e);
		}else if(e instanceof UserNotFindException) {
			return new ResponseResult<Void>(402,e);
		}else if(e instanceof PasswordNotMacthException) {
			return new ResponseResult<Void>(403,e);
		}else if(e instanceof InsertDataException) {
			return new ResponseResult<Void>(501,e);
		}else if(e instanceof UpdateDataException) {
			return new ResponseResult<Void>(502,e);
		}
		return null;
	}
	
	protected Integer getUserId(HttpSession session) {
		return Integer.parseInt(session.getAttribute("uid").toString());
	}
}
