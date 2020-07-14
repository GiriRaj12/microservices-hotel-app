package org.hotelapp.inventory.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.hotelapp.commons.Constants.RabbitMQConstants;
import org.hotelapp.commons.Constants.Services;
import org.hotelapp.commons.Models.MQModel;
import org.hotelapp.commons.Models.Rooms;
import org.hotelapp.commons.Utilities.JsonUtils;
import org.hotelapp.inventory.Utils.MongoUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQService {

    public void recieveMessage() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(RabbitMQConstants.RABBIT_QUEUE, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            doActions(message);
            System.out.println("Job Done::"+message);
        };
        channel.basicConsume(RabbitMQConstants.RABBIT_QUEUE, true, deliverCallback, consumerTag -> { });
    }

    private void doActions(String message) {
        MQModel mqModel = JsonUtils.fromJson(message,MQModel.class);
        if(!mqModel.getTargetService().equals(Services.INVENTORY))
            return;

        if(mqModel.getAction().equals("add")){
            Rooms rooms = MongoUtils.getById(mqModel.getRoomId());
            System.out.println(JsonUtils.toJson(rooms));
            rooms.setAvailableNumber(rooms.getAvailableNumber() + mqModel.getUpdatedCount());
            rooms.setAvailable(rooms.getAvailableNumber() > 0);
            MongoUtils.update(rooms);
        }
        if(mqModel.getAction().equals("remove")){
            Rooms rooms = MongoUtils.getById(mqModel.getRoomId());
            System.out.println(JsonUtils.toJson(rooms));
            rooms.setAvailableNumber(rooms.getAvailableNumber() - mqModel.getUpdatedCount());
            rooms.setAvailable(rooms.getAvailableNumber() > 0);
            MongoUtils.update(rooms);
        }
    }
}
