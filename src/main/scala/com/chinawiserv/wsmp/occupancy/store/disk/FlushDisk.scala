package com.chinawiserv.wsmp.occupancy
package store.disk

import java.util
import java.util.concurrent.LinkedBlockingQueue

import com.chinawiserv.wsmp._
import com.chinawiserv.wsmp.occupancy.model.OccupancyData
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import com.mongodb.client.model._
import org.apache.commons.lang.StringUtils
import org.bson.Document
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConversions

/**
  * Created by zengpzh on 2017/1/6.
  */

private[occupancy] object FlushDisk {

  private val logger: Logger = LoggerFactory.getLogger(FlushDisk.getClass);

  private val flushDiskQueue = new LinkedBlockingQueue[List[OccupancyData]]();

  private val FLUSH_DISK_CONCURRENT_NUM: Int = 10;

  private val flushDiskExecutorService = ThreadPool.newThreadPool(FLUSH_DISK_CONCURRENT_NUM, new CustomThreadFactory("Occupancy-Flush-Disk-Executor-"));

  flushDiskExecutorService.execute(new FlushDiskTask());

  def offer(records: List[OccupancyData]): Unit ={
    if(records != null && !records.isEmpty){
      flushDiskQueue.offer(records);
    }
  }

  private[disk] def flush: Unit = {
    val records = flushDiskQueue.take;
    flushDiskExecutorService.execute(new Runnable {
      override def run(): Unit = {
        val now = System.currentTimeMillis();
        doFlush(records);
      }
    })
  }

  private def doFlush(records: List[OccupancyData]): Unit = {
    if (records != null && !records.isEmpty) {
      try {
        val writeModels = new util.ArrayList[WriteModel[Document]]();
        var collection = "";
        records.foreach(record => {
          val station = record.id;
          val time = record.time;
          val maxLevels = JavaConversions.asJavaCollection[Byte](record.levels);
          if (StringUtils.isNotBlank(time) && time.length == (TIME_YEAR_LENGTH + TIME_DAY_LENGTH) && maxLevels != null) {
            if (collection.isEmpty) {
              collection = collection_prefix + time.take(TIME_YEAR_LENGTH);
            }
            val daytime = time.takeRight(TIME_DAY_LENGTH);
            val filter = Filters.and(Filters.eq("station", station), Filters.eq("time", daytime));
            val replacement = new Document("station", station)
              .append("time", daytime)
              .append("maxLevels", maxLevels);
            val replaceOneModel = new ReplaceOneModel[Document](filter, replacement, new UpdateOptions().upsert(true));
            writeModels.add(replaceOneModel);
          }
        });
        if (writeModels.size > 0 && collection.length == (collection_prefix.length + TIME_DAY_LENGTH)) {
          mongoDB.mc.bulkWrite(mongoDB.dbName, collection, writeModels, new BulkWriteOptions().ordered(false));
          writeModels.clear();
        }
      } catch {
        case e: Exception => e.printStackTrace();
      }
    }
  }

}


