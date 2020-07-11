package org.hotelapp.billing.Services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.hotelapp.billing.Contants.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQService {
    public void publishMessage() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel() ){
            channel.queueDeclare(Constants.RABBIT_QUEUE,false,false,false,null);
            channel.basicPublish("", Constants.RABBIT_QUEUE,null, "Hello World".getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void recieveMessage() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Constants.RABBIT_QUEUE, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(Constants.RABBIT_QUEUE, true, deliverCallback, consumerTag -> { });
    }
}
