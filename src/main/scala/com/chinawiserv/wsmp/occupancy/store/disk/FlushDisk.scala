package com.chinawiserv.wsmp.occupancy
package store.disk

import java.util.concurrent.LinkedBlockingQueue

import com.chinawiserv.wsmp.occupancy.model.OccupancyData
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by zengpzh on 2017/1/6.
  */
private class FlushDisk extends Runnable {

  override def run() {
    while (!Thread.currentThread().isInterrupted) {
      try {
        FlushDisk.flush;
      } catch {
        case e: Exception => Thread.currentThread().interrupt();
      }
    }
  }

}

private[occupancy] object FlushDisk {

  private val logger: Logger = LoggerFactory.getLogger(FlushDisk.getClass);

  private val flushDiskQueue = new LinkedBlockingQueue[List[OccupancyData]]();

  private val FLUSH_DISK_CONCURRENT_NUM: Int = 3;

  private val flushDiskExecutorService = ThreadPool.newThreadPool(FLUSH_DISK_CONCURRENT_NUM, new CustomThreadFactory("Occupancy-Flush-Disk-Executor-"));

  flushDiskExecutorService.execute(new FlushDisk());

  def offer(occupancyDatas: List[OccupancyData]): Unit ={
    if(occupancyDatas != null && !occupancyDatas.isEmpty){
      flushDiskQueue.offer(occupancyDatas);
    }
  }

  private def flush: Unit = {
    logger.info("Occupancy flush DISK, queued: {} ", flushDiskQueue.size);
    flushDiskExecutorService.execute(new FlushDiskTask(FlushDisk.flushDiskQueue.take));
  }

}


