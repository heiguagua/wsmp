package com.chinawiserv.wsmp.occupancy.flush.disk

import java.util
import java.util.concurrent.CountDownLatch

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.Block
import com.mongodb.client.model._
import org.apache.commons.lang.StringUtils
import org.bson.Document

/**
  * Created by zengpzh on 2017/1/6.
  */
class FlushDiskTask(shard: List[Document], time: String, count: CountDownLatch) extends Runnable {

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

object FlushDiskTask {

  private val db = "wsmp";

  private val collection_prefix = "occupancy";

  private val EXISTS_COLLECTIONS = collection.mutable.ListBuffer[String]();

  private def flushRecords(records: List[Document]): Unit = {
    if (records != null && !records.isEmpty) {
      try {
        val writeModels = new util.ArrayList[WriteModel[Document]]();
        var collection = "";
        records.foreach(record => {
          val station = record.getString("station");
          val time = record.getString("time");
          val maxLevels = record.get("maxLevels", classOf[util.Collection[Byte]]);
          if (StringUtils.isNotBlank(station) && StringUtils.isNotBlank(time) && time.length == 8 && maxLevels != null) {
            if (collection.isEmpty) {
              collection = collection_prefix + "_" + time.take(4);
            }
            val daytime = time.takeRight(4);
            val filter = new Document("station", station)
              .append("time", daytime);
            val replacement = new Document("station", station)
              .append("time", daytime)
              .append("maxLevels", maxLevels);
            val replaceOneModel = new ReplaceOneModel[Document](filter, replacement, new UpdateOptions().upsert(true));
            writeModels.add(replaceOneModel);
          }
        });
        if (writeModels.size > 0 && collection.length == (collection_prefix.length + 5)) {
          if (!collectionExists(collection)) {
            synchronized({
              if (!collectionExists(collection)) {
                MongoDB.mc.createCollection(db, collection, null);
                EXISTS_COLLECTIONS += collection;
              }
            });
          }
          MongoDB.mc.bulkWrite(db, collection, writeModels, new BulkWriteOptions().ordered(false));
          writeModels.clear();
        }
      } catch {
        case e: Exception => e.printStackTrace();
      }
    }
  }

  private def collectionExists(collection: String): Boolean = {
    var exists = false;
    if (StringUtils.isNotBlank(collection)) {
      exists = EXISTS_COLLECTIONS.contains(collection);
    }
    if(!exists){
      val mongoIterable = MongoDB.mc.listCollectionNames(db);
      mongoIterable.forEach(new Block[String] {
        override def apply(collectionName: String): Unit = {
          if(collection == collectionName){
            exists = true;
            EXISTS_COLLECTIONS += collection;
            println("====================================")
          }
        }
      })
    }
    exists;
  }

}
