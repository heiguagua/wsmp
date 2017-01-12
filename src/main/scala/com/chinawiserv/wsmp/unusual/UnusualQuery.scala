package com.chinawiserv.wsmp.unusual

import com.dgbc.caculate.CUnusualLevel


object UnusualQuery {

  def main(args: Array[String]): Unit = {

    val un = new CUnusualLevel(10);
/*
    db.Unusual52010001.aggregate([
      {$sort: {dt: -1}},
      {$group: {_id: "$freq", amount: {$sum: 1}, level: {$first: "$level"}, dt: {$first: "$dt"}}},
      {$sort: {_id: 1}}
      ])*/

   /* val pipeline: util.List[Bson] = new util.ArrayList[Bson];
    pipeline.add(Aggregates.sort(new Document("dt", -1)));
    pipeline.add(Aggregates.group(null, new BsonField("count", new Document("$sum", 1))))

    //pipeline.add(Aggregates.project(new Document("_id", 0)))
    val list: util.List[Document] = MongoDB.mc.aggregate("wsmpExt", "Unusual52010001", pipeline);
    println(list);*/
  }

}
