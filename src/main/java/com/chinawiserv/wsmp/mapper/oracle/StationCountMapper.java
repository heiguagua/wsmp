package com.chinawiserv.wsmp.mapper.oracle;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StationCountMapper {

    public Map<String, String> countByYear();
}
