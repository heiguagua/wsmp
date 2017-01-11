package com.chinawiserv.wsmp.handler;

import com.chinawiserv.wsmp.model.Cmd;
import java.util.List;

/**
 * 数据处理接口
 */
public interface DataHandler {

	
    void compute(List<Cmd> cmds);

}
