package com.chinawiserv.wsmp.mongo;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

public class TestMongoDBReplSet {
    public static void main(String[] args)  {
        try {
            List<ServerAddress> addresses = new ArrayList<ServerAddress>();
            ServerAddress address1 = new ServerAddress("127.0.0.1" , 1111);
            ServerAddress address2 = new ServerAddress("127.0.0.1" , 2222);
            ServerAddress address3 = new ServerAddress("127.0.0.1" , 3333);
            addresses.add(address1);
            addresses.add(address2);
            addresses.add(address3);
            MongoClient client = new MongoClient(addresses);
            DB db = client.getDB( "test");
            DBCollection coll = db.getCollection( "test");

            BasicDBObject object = new BasicDBObject();
            object.append( "key1", "value1" );
            ReadPreference preference = ReadPreference.secondary();
            DBObject dbObject = coll.findOne(object, null , preference);
            System. out .println(dbObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
