package com.zjht.manager.action.admin;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexAct {
	@Autowired
	private RedisTemplate<Serializable, Object> redisTemplate;  
	
	@RequestMapping(value="/admin/index.html")
	public String index(HttpServletRequest request, HttpServletResponse response,ModelMap model){

//		redisTemplate.z
		final String s = String.valueOf(2);

		return "";
	}
}
