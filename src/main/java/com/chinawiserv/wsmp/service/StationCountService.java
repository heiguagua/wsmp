package com.chinawiserv.wsmp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.chinawiserv.wsmp.mapper.oracle.StationCountMapper;
import com.chinawiserv.wsmp.pojo.CountResult;

@Service
public class StationCountService {

    @Autowired
    StationCountMapper mapper;

    public List<CountResult> getCurrentYearCount() {
        //框架bug，查询必须传一个分页对象，该对象无实际作用
        return  mapper.countByCurrentYear(new Pagination());
    }

    public List<CountResult> getLastYearCount() {
        //框架bug，查询必须传一个分页对象，该对象无实际作用
        List<CountResult> result = mapper.countBylastYear(new Pagination());
        return result;
    }

}
