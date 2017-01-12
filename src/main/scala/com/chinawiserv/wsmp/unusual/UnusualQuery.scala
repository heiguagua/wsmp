package com.chinawiserv.wsmp.unusual

import java.util.List
import java.util.ArrayList

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.dgbc.caculate.CUnusualLevel
import com.mongodb.client.model.{Aggregates, BsonField}
import org.bson.Document
import org.bson.conversions.Bson


object UnusualQuery {

  def getUnusualById(id: String): List[Document] = {
   // db.Unusual52010001.aggregate([
   //   {$sort: {dt: -1}},
   //   {$group: {_id: "$freq", amount: {$sum: 1}, level: {$first: "$level"}, dt: {$first: "$dt"}}},
   //   {$sort: {_id: 1}}
   //   ])
    val pipeline = new ArrayList[Bson];
    pipeline.add(Aggregates.sort(new Document("dt", -1)));
    pipeline.add(Aggregates.group(new Document("_id", "$freq"),
                                  new BsonField("amount", new Document("$sum", 1)),
                                  new BsonField("level", new Document("$first", "$level")),
                                  new BsonField("dt", new Document("$first", "$dt"))))
    pipeline.add(Aggregates.sort(new Document("_id", 1)));
    MongoDB.mc.aggregate("wsmpExt", "Unusual"+id, pipeline);
  }

  def main(args: Array[String]): Unit = {
    val list = getUnusualById("52010001");
    for (i <- 0.until(list.size())) {
      val doc = list.get(i);
      println(doc);
    }
  }

}
