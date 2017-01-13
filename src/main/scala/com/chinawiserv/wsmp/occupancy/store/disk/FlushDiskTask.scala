package com.chinawiserv.wsmp.occupancy
package store.disk

import java.util

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.client.model._
import org.apache.commons.lang.StringUtils
import org.bson.Document

/**
  * Created by zengpzh on 2017/1/6.
  */
private class FlushDiskTask(shard: List[Document]) extends Runnable {

  override def run(): Unit = {
    if (shard != null) {
      println(Thread.currentThread.getName + "-----------flush shard on disk, shard length: " + shard.length + ", start: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS);
      FlushDiskTask.flushRecords(shard);
      println(Thread.currentThread.getName + "-----------flush shard on disk over, end: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    }
  }

}

private[disk] object FlushDiskTask {

  import com.chinawiserv.wsmp.configuration.SpringContextManager._;

  private val mongoDB  = getBean(classOf[MongoDB]);

  def flushRecords(records: List[Document]): Unit = {
    if (records != null && !records.isEmpty) {
      try {
        val writeModels = new util.ArrayList[WriteModel[Document]]();
        var collection = "";
        records.foreach(record => {
          val station = record.getInteger("station");
          val time = record.getString("time");
          val maxLevels = record.get("maxLevels", classOf[util.Collection[Short]]);
          if (station != null && StringUtils.isNotBlank(time) && time.length == (TIME_YEAR_LENGTH + TIME_DAY_LENGTH) && maxLevels != null) {
            if (collection.isEmpty) {
              collection = collection_prefix + time.take(TIME_YEAR_LENGTH);
            }
            mongoDB.shardCollection(db, collection, new Document("station", 1));
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
          mongoDB.mc.bulkWrite(db, collection, writeModels, new BulkWriteOptions().ordered(false));
          writeModels.clear();
        }
      } catch {
        case e: Exception => e.printStackTrace();
      }
    }
  }

}
