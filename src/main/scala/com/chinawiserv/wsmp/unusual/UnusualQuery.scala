package com.chinawiserv.wsmp;
package unusual;

import java.util
import java.io.File
import java.util.{ArrayList, Date, HashMap, List}
import com.chinawiserv.wsmp.common.ImplFileReader.Files
import com.chinawiserv.wsmp.util.DateTime
import com.codahale.jerkson.Json
import com.mongodb.client.model.{Aggregates, BsonField}
import org.bson.Document
import org.bson.conversions.Bson

object UnusualQuery {

  def getUnusualById(id: String): List[HashMap[String, Object]] = {
   // db.Unusual52010001.aggregate([
   //   {$sort: {dt: -1}},
   //   {$group: {_id: "$freq", amount: {$sum: 1}, level: {$first: "$level"}, dt: {$first: "$dt"}}},
   //   {$sort: {_id: 1}}
   //   ])
    val result = new ArrayList[HashMap[String, Object]]();
    val pipeline = new ArrayList[Bson];
    pipeline.add(Aggregates.sort(new Document("dt", -1)));
    pipeline.add(Aggregates.group(new Document("_id", "$freq"),
                                  new BsonField("amount", new Document("$sum", 1)),
                                  new BsonField("level", new Document("$first", "$level")),
                                  new BsonField("dt", new Document("$first", "$dt"))))
    pipeline.add(Aggregates.sort(new Document("_id", 1)));
    val list = mongoDB.mc.aggregate(mongoDB.dbName, "Unusual"+id, pipeline);
    if (list != null && !list.isEmpty) {
      for (i <- 0.until(list.size())) {
        val doc = list.get(i)
        val map = new HashMap[String, Object]();
        val freq = (doc.get("_id").asInstanceOf[Document].get("_id").toString.toDouble / 1000).toString;
        map.put("freq", freq);
        map.put("level", doc.get("level"));
        val dt = new Date(doc.get("dt").toString.toLong);
        map.put("dt", DateTime.convertDateTime_YYYYMMDDHHMMSS(dt));
        map.put("amount", doc.get("amount"));
        result.add(map);
      }
    }
    return result;
  }

  def readJson(): HashMap[String, HashMap[String, Object]] = {
    val result = new HashMap[String, HashMap[String, Object]];
    val file = new File("F:\\IdeaProject\\DeepOne\\wsmp\\data\\map.json");
    val json = file.lines.mkString;
    val list = Json.parse[List[HashMap[String, Object]]](json);
    if (list != null && !list.isEmpty) {
      for (i <- 0.until(list.size())) {
        val map = list.get(i);
        result.put(map.get("id").toString, map);
      }
    }
    return result;
  }

  def initMap(): util.Collection[HashMap[String, Object]] =  {
    val result = this.readJson();
    if (result != null && !result.isEmpty) {
      val list = mongoDB.mc.find(mongoDB.dbName, "UnusualLevels", new Document(), null);
      if (list != null && !list.isEmpty) {
        for (i <- 0.until(list.size())) {
          val doc = list.get(i);
          val id = doc.get("id").toString;
          val un =  doc.get("un").toString;
          val map = result.get(id);
          if (map != null) {
            map.put("num", un);
          }
        }
      }
    }
    return result.values();
  }

  def main(args: Array[String]): Unit = {
    val list = initMap();
    println(list);

  }

}
