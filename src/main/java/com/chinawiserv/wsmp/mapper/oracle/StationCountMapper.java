package com.chinawiserv.wsmp.mapper.oracle;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.chinawiserv.wsmp.pojo.CountResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StationCountMapper {

    public List<CountResult> countByCurrentYear(Pagination pageInfo);

    public List<CountResult> countBylastYear(Pagination pageInfo);
}
