package com.chinawiserv.wsmp.handler;

import com.chinawiserv.model.Cmd;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 数据处理接口
 */
public interface DataHandler {

    /**
     * 计算接口
     * @param cmds 消息
     */
    void compute(List<Cmd> cmds);

}
