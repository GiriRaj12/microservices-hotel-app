package org.hotelapp.billing.Utils;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.hotelapp.commons.Constants.DatabaseConstants;
import org.hotelapp.commons.Constants.Tables;
import org.hotelapp.commons.Models.Bookings;
import org.hotelapp.commons.Utilities.JsonUtils;

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

    public static void save(Bookings bookings){
        mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Bookings.toString(), Bookings.class).insertOne(bookings);
    }

    public static Bookings getById(String id){
        Bson query = Filters.eq("_id",id);
        for (Bookings bookings : mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Bookings.toString(), Bookings.class).find(query)) {
            return bookings;
        }
        return null;
    }

    public static void deleteById(String id){
        Bson query = Filters.eq("_id",id);
        mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Bookings.toString(), Bookings.class).findOneAndDelete(query);
    }

    public static List<Bookings> get(String userEmail){
        Document document = new Document("userEmail",userEmail);
        List<Bookings> bookings = new ArrayList<>();
        mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Bookings.toString(), Bookings.class).find(document)
                .sort(Sorts.ascending("datelong"))
                .forEach(e -> bookings.add(e));
        return bookings;
    }
}
