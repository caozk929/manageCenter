package com.zjht.manager.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjht.manager.api.common.ApiConstants;
import com.zjht.manager.api.common.CommApiException;
import com.zjht.manager.api.common.ResultDto;
import com.zjht.manager.entity.User;

@Controller
public class UserService {
	
	@ResponseBody
	@RequestMapping(value="/user/list.do")
	public ResultDto<List<User>> getUserList(User user){
		List<User> users = new ArrayList<User>();
		User user1 = new User();
		user1.setUserName("xiaohei");
		user1.setMobile("13229460282");
		users.add(user1);
		User user2 = new User();
		user2.setUserName("xiaobai");
		user2.setMobile("15886098998");
		users.add(user2);
		if("1".equals("1")){
			throw new CommApiException("00001", "参数不对");
		}
		ResultDto<List<User>> dto = new ResultDto<>();
		dto.setStatus(ApiConstants.SUCCESS);
		dto.setMsg(ApiConstants.SUCCESS_MSG);
		dto.setData(users);
		return dto;
	}
}
