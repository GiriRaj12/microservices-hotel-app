package org.hotelapp.inventory.Utils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.hotelapp.commons.Constants.DatabaseConstants;
import org.hotelapp.commons.Constants.Tables;
import org.hotelapp.commons.Models.Rooms;

import java.util.ArrayList;
import java.util.List;

public class MongoUtils {
    private static CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
    );
    private static MongoClientSettings settings = MongoClientSettings.builder()
            .codecRegistry(pojoCodecRegistry)
            .build();

    private static MongoClient mongoClient = MongoClients.create(settings);

    public static void save(Rooms rooms){
        mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Rooms.toString(), Rooms.class).insertOne(rooms);
    }

    public static void update(Rooms rooms){
        mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Rooms.toString(), Rooms.class).findOneAndDelete(new BasicDBObject("_id",rooms.getId()));
        mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Rooms.toString(), Rooms.class).insertOne(rooms);
    }

    public static void delete(String id){
        Bson query = Filters.eq("_id",id);
        mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Rooms.toString(), Rooms.class).deleteOne(query);
    }

    public static List<Rooms> get(){
        Bson query = Filters.eq("available",true);
        List<Rooms> resultList = new ArrayList<>();
        mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Rooms.toString(), Rooms.class).find(query).forEach(e -> resultList.add(e));
        return resultList;
    }

    public static Rooms getById(String id){
        Bson query = Filters.eq("_id",id);
        for (Rooms rooms : mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Rooms.toString(), Rooms.class).find(query)) {
            return rooms;
        }
        return null;
    }

}
