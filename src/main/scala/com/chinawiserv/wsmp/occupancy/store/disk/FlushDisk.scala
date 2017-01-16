package com.chinawiserv.wsmp.occupancy
package store.disk

import java.util.concurrent.LinkedBlockingQueue

import com.chinawiserv.wsmp.occupancy.model.OccupancyData
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import org.bson.Document

import scala.collection.JavaConversions

/**
  * Created by zengpzh on 2017/1/6.
  */
private class FlushDisk extends Thread("Occupancy-Flush-Disk") {

  override def run() {
    while (!Thread.currentThread().isInterrupted) {
      try {
        FlushDisk.flush(FlushDisk.flushDiskQueue.take);
      } catch {
        case e: Exception => Thread.currentThread().interrupt();
      }
    }
  }

}

private[occupancy] object FlushDisk {

  private val flushDiskQueue = new LinkedBlockingQueue[List[OccupancyData]]();

  private val FLUSH_DISK_CONCURRENT_NUM: Int = 15;

  private val flushDiskExecutorService = ThreadPool.newThreadPool(FLUSH_DISK_CONCURRENT_NUM, new CustomThreadFactory("Occupancy-Flush-Disk-Executor-"));

  new FlushDisk().start;

  def offer(occupancyDatas: List[OccupancyData]): Unit ={
    if(occupancyDatas != null && !occupancyDatas.isEmpty){
      flushDiskQueue.offer(occupancyDatas);
    }
  }

  private def flush(occupancyDatas: List[OccupancyData]): Unit = {
    println("------------flushDiskQueue.size = " + flushDiskQueue.size)
    this.start(this.slice(occupancyDatas));
  }

  private def start(shards: (List[List[Document]])): Unit = {
    shards.foreach(shard => {
      FlushDisk.flushDiskExecutorService.execute(new FlushDiskTask(shard));
    });
  }

  private def slice(occupancyDatas: List[OccupancyData]): List[List[Document]] = {
    if (occupancyDatas != null) {
      val records = occupancyDatas.map(occupancyData => {
        new Document("station", occupancyData.id).append("time", occupancyData.time).append("maxLevels", JavaConversions.asJavaCollection[Byte](occupancyData.levels));
      });
      records.sliding(SHARD_SIZE, SHARD_SIZE).toList;
    } else {
      null;
    }
  }

}


