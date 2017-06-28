package com.chinawiserv.wsmp.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.chinawiserv.wsmp.mapper.FrequencyRangeMapper;
import com.chinawiserv.wsmp.pojo.FrequencyRange;
import com.chinawiserv.wsmp.service.IFrequencyRangeService;

/**
 * <p>
 * 频段范围表，分为上行频段和下行频段。 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2017-06-28
 */
@Service
public class FrequencyRangeServiceImpl extends ServiceImpl<FrequencyRangeMapper, FrequencyRange> implements IFrequencyRangeService {
	
}
