package org.hotelapp.inventory.Controller;

import org.hotelapp.inventory.Service.RabbitMQService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@ComponentScan("org.hotelapp.inventory")
@SpringBootApplication
public class HotelInventoryApplication {
    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication.run(HotelInventoryApplication.class, args);
        //connectRabbitMQ();
    }

    private static void connectRabbitMQ() {
        try {
            new RabbitMQService().recieveMessage();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
