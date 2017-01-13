package com.chinawiserv.wsmp.unusual

import java.util.List
import java.util.ArrayList
import java.util.HashMap
import com.chinawiserv.wsmp.mongodb.MongoDB
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
    val list = MongoDB.mc.aggregate("wsmpExt", "Unusual"+id, pipeline);
    for (i <- 0.until(list.size())) {
      val doc = list.get(i)
      val map = new HashMap[String, Object]();
      map.put("freq", doc.get("_id").asInstanceOf[Document].get("_id"));
      map.put("level", doc.get("level"));
      map.put("dt", doc.get("dt"));
      map.put("amount", doc.get("amount"));
      result.add(map);
    }
    return result;
  }

  def main(args: Array[String]): Unit = {
    val list = getUnusualById("52010001");
    for (i <- 0.until(list.size())) {
      val doc = list.get(i);
      println(doc);
      //{dt=1483582717000, amount=142, level=82, id=107.875}
      //{dt=1483582717000, amount=142, level=70, id=107.9}
    }
  }

}
