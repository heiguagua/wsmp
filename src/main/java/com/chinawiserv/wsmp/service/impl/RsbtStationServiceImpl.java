package com.chinawiserv.wsmp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.chinawiserv.wsmp.mapper.RsbtStationMapper;
import com.chinawiserv.wsmp.pojo.FrequencyType;
import com.chinawiserv.wsmp.pojo.RedioDetail;
import com.chinawiserv.wsmp.pojo.RsbtStation;
import com.chinawiserv.wsmp.service.IRsbtStationService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2017-06-29
 */
@Service
public class RsbtStationServiceImpl extends ServiceImpl<RsbtStationMapper, RsbtStation> implements IRsbtStationService {

	public List<RedioDetail> getStationList(FrequencyType frequencyType) {
		List<RedioDetail> reslutDetails = this.baseMapper.getStationDetail(frequencyType);
		return reslutDetails;

	}

}
