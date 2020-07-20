package org.hotelapp.users.MongoUtils;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.hotelapp.commons.Constants.DatabaseConstants;
import org.hotelapp.commons.Constants.Tables;
import org.hotelapp.commons.Models.Users;
import org.hotelapp.commons.Utilities.InternalServiceException;

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
        try {
            mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Users.toString(), Users.class).insertOne(t);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static Users getUserByEmail(String emailId){
        try {
            Bson query = Filters.and(Filters.eq("emailId", emailId));
            for (Users users : mongoClient.getDatabase(DatabaseConstants.DATABASE).getCollection(Tables.Users.toString(), Users.class).find(query)) {
                return users;
            }
        }catch (Exception e){
            throw new InternalServiceException(e.getMessage());
        }
        return null;
    }
}
