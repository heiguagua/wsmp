package com.chinawiserv.wsmp.mongodb

import com.chinawiserv.core.mongo.{MongoDBClient, MongoDBClientProxy}
import com.mongodb._
import org.apache.commons.lang.StringUtils
import org.bson.conversions.Bson

import scala.collection.mutable.ArrayBuffer

object MongoDB {

  lazy val mc = createConnection;

  private val HOST0: String = "172.16.7.210";

  private val HOST1: String = "172.16.7.201";

  private val HOST2: String = "172.16.7.202";

  private val HOST3: String = "172.16.7.203";

  private val HOST4: String = "172.16.7.204";

  private val PORT: Int = 30000;

  private val DB: String = "wsmp";

  private val EXISTS_COLLECTIONS = scala.collection.mutable.Map[String, ArrayBuffer[String]]();

  def createConnection(): MongoDBClient = {
    /*val addresses = new util.ArrayList[ServerAddress];
    addresses.add(new ServerAddress(HOST0, PORT));
    addresses.add(new ServerAddress(HOST1, PORT));
    addresses.add(new ServerAddress(HOST2, PORT));
    addresses.add(new ServerAddress(HOST3, PORT));
    addresses.add(new ServerAddress(HOST4, PORT));
    new MongoDBClientProxy().bind(addresses, DB, buildOptions);*/
    new MongoDBClientProxy().bind("172.16.7.205", 28018, DB, buildOptions);
  }

  def shardCollection(db: String, collectionName: String, shardKey: Bson): Unit = {
    if(StringUtils.isNotBlank(db) && StringUtils.isNotBlank(collectionName) && shardKey != null){
      val collections = EXISTS_COLLECTIONS.getOrElseUpdate(db, ArrayBuffer[String]());
      var exists = collections.contains(collectionName);
      if(!exists){
        val collectionNames = MongoDB.mc.listCollectionNames(db);
        collectionNames.forEach(new Block[String] {
          override def apply(collectionName: String): Unit = {
            if (collectionName == collectionName) {
              collections += collectionName;
              exists = true;
              return;
            }
          }
        });
      }
      if(!exists){
        MongoDB.mc.createCollection(db, collectionName, null);
        collections += collectionName;
      }
      val sharded = MongoDB.mc.getCollectionStats(db, collectionName).getBoolean("sharded");
      if(sharded != null && !sharded){
        MongoDB.mc.shardCollection(db, collectionName, shardKey);
      }
    }
  }

  private def buildOptions: MongoClientOptions = {
    val builder = new MongoClientOptions.Builder;
    builder.connectionsPerHost(100);
    builder.connectTimeout(3000);
    builder.minConnectionsPerHost(1);
    builder.maxConnectionIdleTime(3 * 1000);
    builder.maxConnectionLifeTime(5 * 60 * 1000);
    builder.threadsAllowedToBlockForConnectionMultiplier(1);
    builder.maxWaitTime(3000);
    builder.serverSelectionTimeout(3000);
    builder.writeConcern(WriteConcern.MAJORITY);
    builder.readConcern(ReadConcern.DEFAULT);
    builder.readPreference(ReadPreference.secondary());
    return builder.build();
  }

}


