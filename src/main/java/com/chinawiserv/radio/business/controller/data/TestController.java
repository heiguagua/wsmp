package com.chinawiserv.radio.business.controller.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.chinawiserv.radio.business.pojo.User;
import com.chinawiserv.radio.business.service.UserService;

@Controller
@RequestMapping("/data")
public class TestController {
	@Autowired
	UserService userService;
	
	
	@RequestMapping("/get")
	public Object test() {
		   EntityWrapper<User> ew=new EntityWrapper<User>();
	       ew.setEntity(new User());
	       String name="wang";
	       Integer age=16;
	       ew.where("name = {0}",name).andNew("age > {0}",age).orderBy("age");
	       List<User> list = userService.selectList(ew);
	       return list;
	}
}
