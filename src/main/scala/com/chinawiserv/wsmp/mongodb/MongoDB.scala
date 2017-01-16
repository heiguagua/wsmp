package com.chinawiserv.wsmp.mongodb

import java.util.concurrent.locks.ReentrantReadWriteLock

import com.chinawiserv.core.mongo.{MongoDBClient, MongoDBClientProxy}
import com.mongodb._
import org.apache.commons.lang.StringUtils
import org.bson.Document
import org.bson.conversions.Bson
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import scala.collection.JavaConversions
import scala.collection.mutable.ArrayBuffer

@Component
class MongoDB extends InitializingBean {

  @Value("${mongodb.hosts}")
  var mongodbHosts: String = _;

  @Value("${mongodb.db}")
  var dbName: String = _;

  @Value("${mongodb.shard.enable}")
  var shardEnable: Boolean = _;

  private val EXISTS_COLLECTIONS = ArrayBuffer[String]();

  private var mongoDBClient: MongoDBClient = _;

  def mc: MongoDBClient = {
    this.mongoDBClient;
  }

  override def afterPropertiesSet(): Unit = {
//    mongoDBClient = createConnection;
//    this.enableDbShard(dbName);
  }

  private def enableDbShard(dbName: String): Unit = {
    if (shardEnable && mongodbHosts.contains(",")) {
      val documents = JavaConversions.asScalaBuffer(mc.find("config", "databases", new Document("_id", dbName), null)).toList;
      if (documents.isEmpty) {
        mc.enableDbShard(dbName);
      } else {
        documents.foreach(document => {
          val partitioned = document.getBoolean("partitioned");
          if (partitioned == null || !partitioned) {
            mc.enableDbShard(dbName);
          }
        });
      }
    }
  }

  private def createConnection: MongoDBClient = {
    val addresses = new java.util.ArrayList[ServerAddress]();
    val mongodbHostArray = mongodbHosts.split(",");
    mongodbHostArray.foreach(mongodbHost => {
      val hostAndPort = mongodbHost.split(":");
      if (hostAndPort.length == 2) {
        addresses.add(new ServerAddress(hostAndPort(0), hostAndPort(1).toInt));
      }
    });
    new MongoDBClientProxy().bind(addresses, dbName, this.buildOptions);
  }

  def shardCollection(collection: String, shardKey: Bson): Unit = {
    if (shardEnable && StringUtils.isNotBlank(collection) && shardKey != null) {
      var exists = EXISTS_COLLECTIONS.contains(collection);
      if (!exists) {
        MongoDB.lock.writeLock.lock;
        exists = EXISTS_COLLECTIONS.contains(collection);
        if (!exists) {
          val collectionNames = mc.listCollectionNames(dbName);
          collectionNames.forEach(new Block[String] {
            override def apply(collectionName: String): Unit = {
              if (collection == collectionName) {
                EXISTS_COLLECTIONS += collection;
                exists = true;
              }
            }
          });
          if (!exists) {
            mc.createCollection(dbName, collection, null);
            EXISTS_COLLECTIONS += collection;
            val sharded = mc.getCollectionStats(dbName, collection).getBoolean("sharded");
            if (sharded != null && !sharded) {
              mc.shardCollection(dbName, collection, shardKey);
            }
          }
        }
        MongoDB.lock.writeLock.unlock;
      }
    }
  }

  private def buildOptions: MongoClientOptions = {
    val builder = new MongoClientOptions.Builder;
    builder.connectionsPerHost(20);
    builder.connectTimeout(10000);
    builder.minConnectionsPerHost(1);
    builder.maxConnectionIdleTime(60 * 1000);
    //builder.maxConnectionLifeTime(5 * 60 * 1000);
    builder.threadsAllowedToBlockForConnectionMultiplier(5);
    builder.maxWaitTime(60 * 1000);
    builder.serverSelectionTimeout(3000);
    builder.writeConcern(WriteConcern.MAJORITY);
    builder.readConcern(ReadConcern.DEFAULT);
    builder.readPreference(ReadPreference.secondary());
    return builder.build();
  }
}

object MongoDB {

  private val lock = new ReentrantReadWriteLock();

}



