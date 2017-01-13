package com.chinawiserv.wsmp

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.Block
import com.mongodb.client.model.Filters
import org.bson.conversions.Bson

import scala.collection.JavaConversions
import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Created by zengpzh on 2017/1/8.
  */
package object occupancy {

  private[occupancy] val TIME_FORMAT = "yyyyMMdd";

  private[occupancy] val TIME_YEAR_LENGTH = 4;

  private[occupancy] val TIME_DAY_LENGTH = 4;

  private[occupancy] val db = "wsmp";

  private[occupancy] val collection_prefix = "occupancy_";

  private[occupancy] val EXISTS_COLLECTIONS = ArrayBuffer[String]();

  //实例化该类时就加载磁盘数据到内存，避免延迟加载对第一次调用的影响
  private[occupancy] val OCCUPANCY_MEM = load;

  private def load: Map[String, Map[Int, ArrayBuffer[Short]]] = {
    println(DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    val OCCUPANCY_MEM_DATA = Map[Int, ArrayBuffer[Short]]();
    val time = DateTime.getCurrentDate_YYYYMMDDWithOutSeparator;
    val year = time.take(TIME_YEAR_LENGTH);
    val daytime = time.takeRight(TIME_DAY_LENGTH);
    val collection = collection_prefix + year;
    val filter = Filters.eq("time", daytime);
    val records = JavaConversions.asScalaBuffer(MongoDB.mc.find(db, collection, filter, null)).toArray;
    records.foreach(record => {
      val station = record.getInteger("station").toInt;
      val maxLevels = ArrayBuffer[Short]();
      maxLevels ++= JavaConversions.asScalaBuffer[Int](record.get("maxLevels", classOf[java.util.List[Int]])).map(_.toShort);
      OCCUPANCY_MEM_DATA += (station -> maxLevels);
    });
    println(DateTime.getCurrentDate_YYYYMMDDHHMMSS);
    Map[String, Map[Int, ArrayBuffer[Short]]](time -> OCCUPANCY_MEM_DATA);
  }

  private[occupancy] def shardCollection(collectionName: String, shardKey: Bson): Unit = {
    var exists = EXISTS_COLLECTIONS.contains(collectionName);
    if(!exists){
      val collectionNames = MongoDB.mc.listCollectionNames(db);
      collectionNames.forEach(new Block[String] {
        override def apply(collectionName: String): Unit = {
          if (collectionName == collectionName) {
            EXISTS_COLLECTIONS += collectionName;
            exists = true;
            return;
          }
        }
      });
    }
    if(!exists){
      MongoDB.mc.createCollection(db, collectionName, null);
      EXISTS_COLLECTIONS += collectionName;
    }
    val sharded = MongoDB.mc.getCollectionStats(db, collectionName).getBoolean("sharded");
    if(sharded != null && !sharded){
      MongoDB.mc.shardCollection(db, collectionName, shardKey);
    }
  }



}
