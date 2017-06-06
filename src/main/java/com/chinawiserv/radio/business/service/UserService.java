package com.chinawiserv.radio.business.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.chinawiserv.radio.business.mapper.UserMapper;
import com.chinawiserv.radio.business.pojo.User;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

	public Page<User> selectUserPage(Page<User> page, String state) {
		page.setRecords(this.baseMapper.selectUserList(page, state));
		return page;
	}
}
