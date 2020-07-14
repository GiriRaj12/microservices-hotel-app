package org.hotelapp.inventory.Controller;

import org.hotelapp.inventory.Service.RabbitMQService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class HotelInventoryApplication {
    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication.run(HotelInventoryApplication.class, args);
        new RabbitMQService().recieveMessage();
    }
}
