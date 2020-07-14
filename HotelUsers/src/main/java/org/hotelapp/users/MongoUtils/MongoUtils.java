package org.hotelapp.users.MongoUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import jdk.javadoc.internal.doclets.formats.html.markup.Table;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.hotelapp.commons.Constants.DatabaseConstants;
import org.hotelapp.commons.Constants.Tables;
import org.hotelapp.commons.Models.Users;

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
        mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Users.toString(), Users.class).insertOne(t);
    }

    public static Users getUserByEmail(String emailId){
        Bson query = Filters.and(Filters.eq("emailId",emailId));
        for (Users users : mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Users.toString(), Users.class).find(query)) {
            return users;
        }
        return null;
    }
}
