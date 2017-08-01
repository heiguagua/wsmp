package com.chinawiserv.wsmp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.chinawiserv.wsmp.pojo.FrequencyRange;

/**
 * <p>
 * 频段范围表，分为上行频段和下行频段。 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2017-06-28
 */
@Mapper
public interface FrequencyRangeMapper extends BaseMapper<FrequencyRange> {

}