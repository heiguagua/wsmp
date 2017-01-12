package com.chinawiserv.wsmp.unusual

import java.util.List
import java.util.ArrayList

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.dgbc.caculate.CUnusualLevel
import com.mongodb.client.model.{Aggregates, BsonField}
import org.bson.Document
import org.bson.conversions.Bson


object UnusualQuery {

  def main(args: Array[String]): Unit = {

    val un = new CUnusualLevel(10);
    /*
    db.Unusual52010001.aggregate([
        {$sort: {dt: -1}},
        {$group: {_id: "$freq", amount: {$sum: 1}, level: {$first: "$level"}, dt: {$first: "$dt"}}},
        {$sort: {_id: 1}}
    ])
          */

    val pipeline: List[Bson] = new ArrayList[Bson];
    pipeline.add(Aggregates.sort(new Document("dt", -1)));
    pipeline.add(Aggregates.group(null, new BsonField("count", new Document("$sum", 1))))

    //pipeline.add(Aggregates.project(new Document("_id", 0)))
    val list: List[Document] = MongoDB.mc.aggregate("wsmpExt", "Unusual52010001", pipeline);
    println(list);
  }

}
