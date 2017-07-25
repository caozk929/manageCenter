package com.zjht.manager.action.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexAct {
	@Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;  
	
	@RequestMapping(value="/index/index.html")
	public String index(HttpServletRequest request, HttpServletResponse response,ModelMap model){
		kafkaTemplate.sendDefault("nihao");
		return "";
	}
}
