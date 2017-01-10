package com.chinawiserv.wsmp.occupancy
package store.disk

import java.util
import java.util.concurrent.CountDownLatch

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.client.model._
import org.apache.commons.lang.StringUtils
import org.bson.Document

/**
  * Created by zengpzh on 2017/1/6.
  */
private[disk] class FlushDiskTask(shard: List[Document], time: String, count: CountDownLatch) extends Runnable {

  override def run(): Unit = {
    if (shard != null && StringUtils.isNotBlank(time)) {
      println(Thread.currentThread.getName + "-----------flush shard on disk, shard length: " + shard.length + ", start: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS);
      FlushDiskTask.flushRecords(shard);
      println(Thread.currentThread.getName + "-----------flush shard on disk over, end: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    }
    if (count != null) {
      count.countDown();
    }
  }

}

private[disk] object FlushDiskTask {

  def flushRecords(records: List[Document]): Unit = {
    if (records != null && !records.isEmpty) {
      try {
        val writeModels = new util.ArrayList[WriteModel[Document]]();
        var collection = "";
        records.foreach(record => {
          val station = record.getInteger("station");
          val time = record.getString("time");
          val maxLevels = record.get("maxLevels", classOf[util.Collection[Byte]]);
          if (station != null && StringUtils.isNotBlank(time) && time.length == 8 && maxLevels != null) {
            if (collection.isEmpty) {
              collection = collection_prefix + time.take(4);
            }
            val daytime = time.takeRight(4);
            val filter = Filters.and(Filters.eq("station", station), Filters.eq("time", daytime));
            val replacement = new Document("station", station)
              .append("time", daytime)
              .append("maxLevels", maxLevels);
            val replaceOneModel = new ReplaceOneModel[Document](filter, replacement, new UpdateOptions().upsert(true));
            writeModels.add(replaceOneModel);
          }
        });
        if (writeModels.size > 0 && collection.length == (collection_prefix.length + 5)) {
          checkCollection(collection);
          MongoDB.mc.bulkWrite(db, collection, writeModels, new BulkWriteOptions().ordered(false));
          writeModels.clear();
        }
      } catch {
        case e: Exception => e.printStackTrace();
      }
    }
  }

}
