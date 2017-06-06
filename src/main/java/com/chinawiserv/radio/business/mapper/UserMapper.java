package com.chinawiserv.radio.business.mapper;

import java.util.List;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.chinawiserv.radio.business.pojo.User;


public interface UserMapper extends BaseMapper<User> {

	List<User> selectUserList(Pagination page, String state);

	String test();
}
