package com.zjht.manager.action.admin;

import com.zjht.manager.entity.User;
import com.zjht.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Controller
public class IndexAct {
	@Autowired
	private RedisTemplate<Serializable, Object> redisTemplate;  
	@Autowired
	UserService userService;

	@RequestMapping(value="/index/index.html")
	public String index(HttpServletRequest request, HttpServletResponse response,ModelMap model){

		ValueOperations operations= redisTemplate.opsForValue();
		User u = new User();
		u.setUserName("xiaohei");
		u.setId(1L);
		operations.set("userId" + u.getId(),u);
		final String s = String.valueOf(2);

		Object o = operations.get("userId" + u.getId());
		return "index/index";
	}

	@RequestMapping(value="/index/login.html")
	public String login(HttpServletRequest request, HttpServletResponse response,ModelMap model){
		User user = userService.getUserById(1L);
		System.out.println(user);
		return "index/login";
	}
}
