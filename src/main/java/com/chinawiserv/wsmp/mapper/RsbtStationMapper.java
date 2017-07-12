package com.chinawiserv.wsmp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.chinawiserv.wsmp.pojo.FrequencyType;
import com.chinawiserv.wsmp.pojo.RedioDetail;
import com.chinawiserv.wsmp.pojo.RsbtStation;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2017-06-29
 */
public interface RsbtStationMapper extends BaseMapper<RsbtStation> {

	public List<RedioDetail> getStationDetail(FrequencyType frequencyType);
}