package com.chinawiserv.wsmp.occupancy
package store

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

  private[occupancy] val db = "wsmp";

  private[occupancy] val collection_prefix = "occupancy_";

  private[occupancy] def getOccupancyRate(time: String, thresholdVal: Short): List[Document] = {
    if (StringUtils.isNotBlank(time) && time.length == (TIME_YEAR_LENGTH + TIME_DAY_LENGTH)) {
      val year = time.take(TIME_YEAR_LENGTH);
      val daytime = time.takeRight(TIME_DAY_LENGTH);
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
      JavaConversions.asScalaBuffer(MongoDB.mc.aggregate(db, collection, pipeline)).toList;
    } else {
      List();
    }
  }

}
