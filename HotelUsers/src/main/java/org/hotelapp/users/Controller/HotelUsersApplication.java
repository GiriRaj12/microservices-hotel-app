package org.hotelapp.users.Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({"org.hotelapp.users"})
@SpringBootApplication
public class HotelUsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelUsersApplication.class, args);
    }
}
