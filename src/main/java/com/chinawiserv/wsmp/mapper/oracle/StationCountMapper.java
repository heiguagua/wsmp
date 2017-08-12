package com.chinawiserv.wsmp.mapper.oracle;

import com.chinawiserv.wsmp.pojo.CountResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StationCountMapper {

    public List<CountResult> countByCurrentYear();

    public List<CountResult> countBylastYear();
}
