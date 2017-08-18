package com.zjht.manager.action.admin;

import com.zjht.manager.entity.User;
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
	
	@RequestMapping(value="/admin/index.html")
	public String index(HttpServletRequest request, HttpServletResponse response,ModelMap model){

		ValueOperations operations= redisTemplate.opsForValue();
		User u = new User();
		u.setUserName("xiaohei");
		u.setId(1L);
		operations.set("userId" + u.getId(),u);
		final String s = String.valueOf(2);

		return "";
	}
}
