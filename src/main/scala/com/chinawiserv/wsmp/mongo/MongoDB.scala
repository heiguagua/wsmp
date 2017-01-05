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

  private val HOST0: String = "172.16.7.210";

  private val HOST1: String = "172.16.7.201";

  private val HOST2: String = "172.16.7.202";

  private val HOST3: String = "172.16.7.203";

  private val HOST4: String = "172.16.7.204";

  private val PORT: Int = 30000;

  private val DB: String = "wsmp";

  private val BULK_SIZE: Int = 50000;

  //private val executorService = Executors.newCachedThreadPool();

  def createConnection(): MongoDBClient = {
    val addresses = new util.ArrayList[ServerAddress];
    addresses.add(new ServerAddress(HOST0, PORT));
    addresses.add(new ServerAddress(HOST1, PORT));
    addresses.add(new ServerAddress(HOST2, PORT));
    addresses.add(new ServerAddress(HOST3, PORT));
    addresses.add(new ServerAddress(HOST4, PORT));
    new MongoDBClientProxy().bind(addresses, DB, buildOptions)
  }

  def insertRecords(records: List[List[Document]]) = {
    if (records != null) {
      records.foreach(list => {
        //executorService.submit(new Runnable {
          //override def run(): Unit = {
            insertRecord(list);
          //}
        //});
      });
    }
  }

  def insertRecord(record: List[Document]) = {
    if (record != null) {
      println("--------------------------start: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS)
      val insertList = new util.ArrayList[Document];
      try {
        record.foreach(document => {
          insertList.add(document);
        });
        if (insertList.size() > 0) {
          println("-----size: " + insertList.size());
          val s = System.currentTimeMillis();
          mc.insert(DB, "radio", insertList, new InsertManyOptions().ordered(false));
          println(System.currentTimeMillis() - s);
          insertList.clear();
        }
      } catch {
        case e: Exception => e.printStackTrace();
      }
      println("--------------------------end: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS)
    }
  }

  def saveRecords(records: List[List[Document]]) = {
    if (records != null) {
      records.foreach(list => {
        //executorService.submit(new Runnable {
          //override def run(): Unit = {
            saveRecord(list);
          //}
        //});
      });
    }
  }

  def saveRecord(record: List[Document]) = {
    if (record != null) {
      println("--------------------------start: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS)
      val writeModelList = new util.ArrayList[WriteModel[Document]];
      try {
        record.foreach(document => {
          val station = document.getInteger("station");
          val index = document.getInteger("index");
          val level = document.getInteger("level");
          val updateOneModel = new UpdateOneModel[Document](Filters.and(Filters.eq("station", station), Filters.eq("index", index)), Updates.max("level", level), new UpdateOptions().upsert(true));
          writeModelList.add(updateOneModel);
          if (writeModelList.size() % BULK_SIZE == 0) {
            mc.bulkWrite(DB, "radio", writeModelList, new BulkWriteOptions().ordered(false));
            writeModelList.clear();
          }
        });
        if (writeModelList.size() > 0) {
          mc.bulkWrite(DB, "radio", writeModelList, new BulkWriteOptions().ordered(false));
          writeModelList.clear();
        }
      } catch {
        case e: Exception => e.printStackTrace();
      }
      println("--------------------------end: " + DateTime.getCurrentDate_YYYYMMDDHHMMSS)
    }
  }

  private def buildOptions: MongoClientOptions = {
    val builder = new MongoClientOptions.Builder;
    builder.socketKeepAlive(false);
    builder.connectionsPerHost(3000);
    builder.connectTimeout(5000);
    builder.socketTimeout(600000);
    builder.threadsAllowedToBlockForConnectionMultiplier(300);
    builder.maxWaitTime(5000);
    builder.serverSelectionTimeout(5000);
    builder.writeConcern(WriteConcern.MAJORITY);
    builder.readConcern(ReadConcern.DEFAULT);
    builder.readPreference(ReadPreference.secondary());
    return builder.build();
  }

  def main(args: Array[String]): Unit = {
    println("start")
    val stationNum = 300;
    for (station <- 1 to stationNum) {
      val records = ListBuffer[List[Document]]();
      val row = ListBuffer[Document]();
      for (index <- 0 until 200000) {
        val document = new Document("station", station);
        document.append("index", index).append("level", Random.nextInt(127));
        row += document;
      }
      records += row.toList;
      MongoDB.insertRecords(records.toList);
    }
    //MongoDB.saveRecords(records.toList);

  }

}


