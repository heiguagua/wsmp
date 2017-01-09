package com.chinawiserv.wsmp.mongodb

import java.util

import com.chinawiserv.core.mongo.{MongoDBClient, MongoDBClientProxy}
import com.chinawiserv.wsmp.occupancy.Occupancy
import com.chinawiserv.wsmp.util.DateTime
import com.mongodb.client.model._
import com.mongodb.{MongoClientOptions, ReadConcern, ReadPreference, WriteConcern}
import org.bson.Document

object MongoDB {

  lazy val mc = createConnection;

  private val HOST0: String = "172.16.7.210";

  private val HOST1: String = "172.16.7.201";

  private val HOST2: String = "172.16.7.202";

  private val HOST3: String = "172.16.7.203";

  private val HOST4: String = "172.16.7.204";

  private val PORT: Int = 30000;

  private val DB: String = "wsmp";

  def createConnection(): MongoDBClient = {
    /*val addresses = new util.ArrayList[ServerAddress];
    addresses.add(new ServerAddress(HOST0, PORT));
    addresses.add(new ServerAddress(HOST1, PORT));
    addresses.add(new ServerAddress(HOST2, PORT));
    addresses.add(new ServerAddress(HOST3, PORT));
    addresses.add(new ServerAddress(HOST4, PORT));
    new MongoDBClientProxy().bind(addresses, DB, buildOptions);*/
    new MongoDBClientProxy().bind("192.168.86.50", 28018, DB, buildOptions);
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


