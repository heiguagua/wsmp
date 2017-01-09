package com.chinawiserv.wsmp.occupancy.flush

import com.chinawiserv.wsmp.mongodb.MongoDB
import com.mongodb.Block
import org.apache.commons.lang.StringUtils

/**
  * Created by Administrator on 2017/1/9.
  */
package object disk {

  private val EXISTS_COLLECTIONS = collection.mutable.ListBuffer[String]();

  private[occupancy] val db = "wsmp";

  private[occupancy] val collection_prefix = "occupancy";

  private[disk] def checkCollection(collection: String): Unit ={
    if (!collectionExists(collection)) {
      synchronized({
        if (!collectionExists(collection)) {
          MongoDB.mc.createCollection(db, collection, null);
          EXISTS_COLLECTIONS += collection;
        }
      });
    }
  }

  private def collectionExists(collection: String): Boolean = {
    var exists = false;
    if (StringUtils.isNotBlank(collection)) {
      exists = EXISTS_COLLECTIONS.contains(collection);
    }
    if(!exists){
      val mongoIterable = MongoDB.mc.listCollectionNames(db);
      mongoIterable.forEach(new Block[String] {
        override def apply(collectionName: String): Unit = {
          if(collection == collectionName){
            exists = true;
            EXISTS_COLLECTIONS += collection;
          }
        }
      })
    }
    exists;
  }

}
