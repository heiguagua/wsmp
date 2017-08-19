package com.chinawiserv.wsmp.service;

import com.chinawiserv.wsmp.mapper.oracle.StationCountMapper;
import com.chinawiserv.wsmp.pojo.CountResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationCountService {

    @Autowired
    StationCountMapper mapper;

    public List<CountResult> getCurrentYearCount() {
        return  mapper.countByCurrentYear();
    }

    public List<CountResult> getLastYearCount() {
        return mapper.countBylastYear();
    }

}
