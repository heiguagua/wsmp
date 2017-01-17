package com.chinawiserv.wsmp
package occupancy
package store.disk

import java.util

import com.chinawiserv.wsmp.occupancy.model.OccupancyData
import com.mongodb.client.model._
import org.apache.commons.lang.StringUtils
import org.bson.Document
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConversions

/**
  * Created by zengpzh on 2017/1/6.
  */
private class FlushDiskTask(records: List[OccupancyData]) extends Runnable {

  private val logger: Logger = LoggerFactory.getLogger(classOf[FlushDiskTask]);

  override def run(): Unit = {
    if (records != null) {
      val now = System.currentTimeMillis();
      FlushDiskTask.flushRecords(records);
      logger.info("Occupancy flush DISK, execute time: {} {}", System.currentTimeMillis() - now, "MS");
    }
  }

}

private[disk] object FlushDiskTask {

  def flushRecords(records: List[OccupancyData]): Unit = {
    if (records != null && !records.isEmpty) {
      try {
        val writeModels = new util.ArrayList[WriteModel[Document]]();
        var collection = "";
        records.foreach(record => {
          val station = record.id;
          val time = record.time;
          val maxLevels = JavaConversions.asJavaCollection[Byte](record.levels);
          if (station != null && StringUtils.isNotBlank(time) && time.length == (TIME_YEAR_LENGTH + TIME_DAY_LENGTH) && maxLevels != null) {
            if (collection.isEmpty) {
              collection = collection_prefix + time.take(TIME_YEAR_LENGTH);
            }
            mongoDB.shardCollection(collection, new Document("station", 1));
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
