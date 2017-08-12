package com.chinawiserv.wsmp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinawiserv.wsmp.mapper.oracle.StationCountMapper;

@Service
public class StationCountService {

    @Autowired
    StationCountMapper mapper;

    public void getCurrentYearCount() {
        System.out.println(mapper.countByCurrentYear());
    }

    public void getLastYearCount() {
        System.out.println(mapper.countBylastYear());
    }

}
