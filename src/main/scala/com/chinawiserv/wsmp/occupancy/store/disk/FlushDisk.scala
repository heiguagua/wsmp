package com.chinawiserv.wsmp.occupancy
package store
package disk

import java.util.concurrent.CountDownLatch

import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import org.bson.Document

import scala.collection.JavaConversions
import scala.collection.mutable._

/**
  * Created by zengpzh on 2017/1/6.
  */
private class FlushDisk extends Thread("Occupancy-Flush-Disk") {

  override def run() {
    while (!Thread.currentThread().isInterrupted) {
      try {
        if (NEED_FLUSH_DISK) {
          FlushDisk.flush;
        }
        Thread.sleep(1000);
      } catch {
        case e: Exception => Thread.currentThread().interrupt();
      }
    }
  }

}

private[store] object FlushDisk {

  var IS_FLUSHING: Boolean = false;

  private val SHARD_SIZE: Int = 100;

  private val FLUSH_DISK_CONCURRENT_NUM: Int = 6;

  private val flushDiskExecutorService = ThreadPool.newThreadPool(FLUSH_DISK_CONCURRENT_NUM, new CustomThreadFactory("Occupancy-Flush-Disk-Executor-"));

  def start(): Unit = {
    new FlushDisk().start;
  }

  private def flush(): Unit = {
    if (!IS_FLUSHING) {
      doFlush;
    }
  }

  private def doFlush {
    IS_FLUSHING = true;
    NEED_FLUSH_DISK = false;
    val preparedList = prepare;
    start(preparedList);
  }

  private def prepare: List[(String, List[List[Document]], CountDownLatch)] = {
    val preparedList = ListBuffer[(String, List[List[Document]], CountDownLatch)]();
    for ((time, data) <- OCCUPANCY_MEM.toMap) {
      val shards = this.slice(time, data);
      if (shards != null) {
        val count = new CountDownLatch(shards.size);
        preparedList += ((time, shards, count));
      }
    }
    preparedList.toList;
  }

  private def start(preparedList: List[(String, List[List[Document]], CountDownLatch)]): Unit = {
    preparedList.foreach(prepared => {
      val time = prepared._1;
      val shards = prepared._2;
      val count = prepared._3;
      this.startCheck(count);
      shards.foreach(shard => {
        FlushDisk.flushDiskExecutorService.execute(new FlushDiskTask(shard, time, count));
      });
    });
  }

  private def slice(time: String, OCCUPANCY_MEM_DATA: Map[Int, ArrayBuffer[Byte]]): List[List[Document]] = {
    if (OCCUPANCY_MEM_DATA != null) {
      val records = ListBuffer[Document]();
      for ((id, maxLevels) <- OCCUPANCY_MEM_DATA) {
        val record = new Document("station", id).append("time", time).append("maxLevels", JavaConversions.asJavaCollection[Byte](maxLevels));
        records += record;
      }
      val shards = records.toList.sliding(FlushDisk.SHARD_SIZE, FlushDisk.SHARD_SIZE);
      shards.toList;
    } else {
      null;
    }
  }

  private def startCheck(count: CountDownLatch): Unit = {
    new Checker(count).start();
  }

  private class Checker(count: CountDownLatch) extends Thread("Occupancy-Flush-Disk-Checker") {

    override def run() {
      if (count != null) {
        count.await();
      }
      IS_FLUSHING = false;
    }

  }

}


