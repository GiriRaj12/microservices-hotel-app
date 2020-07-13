package org.hotelapp.users.MongoUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.hotelapp.commons.Models.Users;
import org.hotelapp.commons.Utilities.JsonUtils;

public class MongoUtils {
    private static CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
    );
    private static MongoClientSettings settings = MongoClientSettings.builder()
            .codecRegistry(pojoCodecRegistry)
            .build();

    private static MongoClient mongoClient = MongoClients.create(settings);

    public static void saveUser(Users t){
        mongoClient.getDatabase("User").getCollection("Users", Users.class).insertOne(t);
    }

    public static Users getUserByEmail(String emailId){
        Bson query = Filters.and(Filters.eq("emailId",emailId));
        for (Document document : mongoClient.getDatabase("User").getCollection("Users").find(query)) {
            return JsonUtils.fromJson(document.toJson(),Users.class);
        }
        return null;
    }
}
