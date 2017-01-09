package com.chinawiserv.wsmp.occupancy
package flush.disk

import java.util.concurrent.CountDownLatch

import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import org.bson.Document

import scala.collection.JavaConversions
import scala.collection.mutable._

/**
  * Created by zengpzh on 2017/1/6.
  */
object FlushDisk{

  private val SHARD_SIZE: Int = 100;

  private val FLUSH_DISK_CONCURRENT_NUM: Int = 6;

  private var IS_FLUSHING = false;

  private val flushDiskExecutorService = ThreadPool.newThreadPool(FLUSH_DISK_CONCURRENT_NUM, new CustomThreadFactory("Occupancy-Flush-Disk-Executor-"));

  def flush(): Unit ={
    if(!FlushDisk.IS_FLUSHING){
      synchronized({
        if(!FlushDisk.IS_FLUSHING){
          doFlush;
        }
      });
    }
  }

  private def doFlush{
    FlushDisk.IS_FLUSHING = true;
    val preparedList = prepare;
    start(preparedList);
  }

  private def prepare: List[(String, List[List[Document]], CountDownLatch)] = {
    val preparedList = ListBuffer[(String, List[List[Document]], CountDownLatch)]();
    for((time, data) <- OCCUPANCY_MEM.toMap){
      val shards = this.slice(time, data);
      if(shards != null){
        val count = new CountDownLatch(shards.size);
        preparedList += ((time, shards, count));
      }
    }
    preparedList.toList;
  }

  private def start(preparedList: List[(String, List[List[Document]], CountDownLatch)]): Unit ={
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

  private def slice(time: String, OCCUPANCY_MEM_DATA: Map[String, ArrayBuffer[Byte]]): List[List[Document]] = {
    if(OCCUPANCY_MEM_DATA != null){
      val records = ListBuffer[Document]();
      for((id, maxLevels) <- OCCUPANCY_MEM_DATA){;
        val record = new Document("station", id).append("time", time).append("maxLevels", JavaConversions.asJavaCollection[Byte](maxLevels));
        records += record;
      }
      val shards = records.toList.sliding(FlushDisk.SHARD_SIZE, FlushDisk.SHARD_SIZE);
      shards.toList;
    }else{
      null;
    }
  }

  private[this] def startCheck(count: CountDownLatch): Unit ={
    new Checker(count).start();
  }

  private[this] class Checker(count: CountDownLatch) extends Thread("Occupancy-Flush-Disk-Checker"){

    override def run() {
      println(this.getName + "------------flush disk start...");
      if(count != null){
        count.await();
      }
      FlushDisk.IS_FLUSHING = false;
      println(this.getName + "------------flush disk over...");
    }
  }

}


