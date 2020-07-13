package org.hotelapp.users.Controller;

import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages =  {"org.hotelapp"})
@SpringBootApplication
public class HotelUsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelUsersApplication.class, args);
    }
}
