package com.chinawiserv.wsmp.occupancy.store

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.mongodb.Block
import com.mongodb.client.model.{Aggregates, Filters}
import org.apache.commons.lang.StringUtils
import org.bson._
import org.bson.conversions.Bson

import scala.collection.JavaConversions
import scala.collection.mutable.ListBuffer

/**
  * Created by Administrator on 2017/1/9.
  */
package object disk {

  private val EXISTS_COLLECTIONS = collection.mutable.ListBuffer[String]();

  private[occupancy] val db = "wsmp";

  private[occupancy] val collection_prefix = "occupancy_";

  private[occupancy] def getOccupancyRate(time: String, thresholdVal: Byte): List[Document] = {
    if (StringUtils.isNotBlank(time) && time.length == 8) {
      val year = time.take(4);
      val daytime = time.takeRight(4);
      val collection = collection_prefix + year;
      val pipeline = new java.util.ArrayList[Bson]();
      val threshold = ListBuffer[BsonValue](new BsonString("$$item"), new BsonInt32(thresholdVal));
      val occupancyNum = new Document("$size",
        new Document("$filter",
          new Document("input", "$maxLevels").append("as", "item").append("cond", new Document("$gt", JavaConversions.asJavaCollection(threshold)))));
      val totalNum = new Document("$size", "$maxLevels");
      val divide = ListBuffer[Bson](occupancyNum, totalNum);
      val occupancyRate = new Document("$divide", JavaConversions.asJavaCollection(divide));
      pipeline.add(Aggregates.`match`(Filters.eq("time", daytime)));
      pipeline.add(Aggregates.project(new Document("_id", 0).append("time", 1).append("station", 1)
        .append("occupancyRate", occupancyRate)));
      pipeline.add(Aggregates.sort(new Document("station", 1)));
      checkCollection(collection);
      JavaConversions.asScalaBuffer(MongoDB.mc.aggregate(db, collection, pipeline)).toList;
    } else {
      List();
    }
  }

  private[store] def checkCollection(collection: String): Unit = {
    if (!collectionExists(collection)) {
      synchronized({
        if (!collectionExists(collection)) {
          MongoDB.mc.createCollection(db, collection, null);
          MongoDB.mc.createIndex(db, collection, "occupancyTimeIndex", new Document("time", "-1"))
          EXISTS_COLLECTIONS += collection;
        }
      });
    }
  }

  private def collectionExists(collection: String): Boolean = {
    var exists = false;
    if (StringUtils.isNotBlank(collection)) {
      exists = EXISTS_COLLECTIONS.contains(collection);
    }
    if (!exists) {
      val mongoIterable = MongoDB.mc.listCollectionNames(db);
      mongoIterable.forEach(new Block[String] {
        override def apply(collectionName: String): Unit = {
          if (collection == collectionName) {
            exists = true;
            EXISTS_COLLECTIONS += collection;
          }
        }
      })
    }
    exists;
  }

}
