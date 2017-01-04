package com.chinawiserv.wsmp.mongo

import java.util
import java.util.concurrent.Executors

import com.chinawiserv.core.mongo.{MongoDBClient, MongoDBClientProxy}
import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.{MongoClientOptions, ReadConcern, ReadPreference, ServerAddress, WriteConcern}
import com.mongodb.client.model._
import org.bson.Document

import scala.collection.mutable.ListBuffer
import scala.util.Random

object MongoDB {

  private lazy val mc = createConnection;

  private val HOST1: String = "172.16.7.202";

  private val HOST2: String = "172.16.7.203";

  private val HOST3: String = "172.16.7.204";

  private val PORT: Int = 30000;

  private val DB: String = "wsmp";

  private val executorService = Executors.newCachedThreadPool();

  def createConnection(): MongoDBClient = {
    val addresses = new util.ArrayList[ServerAddress];
    addresses.add(new ServerAddress(HOST1, PORT))
    addresses.add(new ServerAddress(HOST2, PORT));
    addresses.add(new ServerAddress(HOST3, PORT));
    new MongoDBClientProxy().bind(addresses, DB)
  }

  def saveRecords(records: List[List[Document]]) = {
    if (records != null) {
      records.foreach(list => {
        executorService.submit(new Runnable {
          override def run(): Unit = {
            saveRecord(list);
          }
        });
      });
    }
  }

  def saveRecord(record: List[Document]) = {
    if (record != null) {
      println("--------------------------start: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS)
      val writeModelList = new util.ArrayList[WriteModel[Document]];
      record.foreach(document => {
        val station = document.getInteger("station");
        val index = document.getInteger("index");
        val level = document.getInteger("level");
        val updateOneModel = new UpdateOneModel[Document](Filters.and(Filters.eq("station", station), Filters.eq("index", index)), Updates.max("level", level), new UpdateOptions().upsert(true));
        writeModelList.add(updateOneModel);
      });
      if(writeModelList.size() > 0){
        mc.bulkWrite(DB, "ratio", writeModelList, new BulkWriteOptions().ordered(false));
        writeModelList.clear();
      }
      println("--------------------------end: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS)
    }
  }

  private def buildOptions: MongoClientOptions = {
        val builder = new MongoClientOptions.Builder;
        builder.socketKeepAlive(false);
        builder.connectionsPerHost(1000);
        builder.connectTimeout(5000);
        builder.socketTimeout(30000);
        builder.threadsAllowedToBlockForConnectionMultiplier(1000);
        builder.maxWaitTime(5000);
        builder.serverSelectionTimeout(3000);
        builder.writeConcern(WriteConcern.MAJORITY);
        builder.readConcern(ReadConcern.DEFAULT);
        builder.readPreference(ReadPreference.secondary());
        return builder.build();
    }

  def main(args: Array[String]): Unit = {
    println("start")
    val stationNum = 300;
    val records = ListBuffer[List[Document]]();
    for (station <- 1 to stationNum) {
      executorService.submit(new Runnable {
        override def run(): Unit = {
          val row = ListBuffer[Document]();
          for (index <- 0 until 1400) {
            val document = new Document("station", station);
            document.append("index", index).append("level", Random.nextInt(127));
            row += document;
          }
          records += row.toList;
        }
      })
    }
    //println("--------------------------start: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS)
    MongoDB.saveRecords(records.toList);
    //println("--------------------------end: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS)
  }

}


