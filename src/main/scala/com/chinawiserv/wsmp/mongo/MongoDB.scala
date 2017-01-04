package com.chinawiserv.wsmp.mongo

import java.util

import com.mongodb.{MongoClient, ServerAddress}
import org.bson.Document

object MongoDB {

  private lazy val mc = createConnection;

  def createConnection(): MongoClient = {
    val addresses = new util.ArrayList[ServerAddress];
    val address1 = new ServerAddress("127.0.0.1", 1111);
    val address2 = new ServerAddress("127.0.0.1", 2222);
    val address3 = new ServerAddress("127.0.0.1", 3333);
    addresses.add(address1);
    addresses.add(address2);
    addresses.add(address3);
    return new MongoClient(addresses);
  }

  def saveRecords(records: List[List[Document]]) = {
    if (records != null) {
      println("records.length="+records.length);
    }
  }

  def saveRecord(record: List[Document]) = {
    if (record != null) {
      println("record.length="+record.length);
    }
  }

}
