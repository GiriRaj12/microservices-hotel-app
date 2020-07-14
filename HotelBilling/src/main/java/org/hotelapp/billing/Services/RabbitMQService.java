package org.hotelapp.billing.Services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.hotelapp.commons.Constants.RabbitMQConstants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQService {
    public void publishMessage(String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel() ){
            channel.queueDeclare(RabbitMQConstants.RABBIT_QUEUE,false,false,false,null);
            channel.basicPublish("", RabbitMQConstants.RABBIT_QUEUE,null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" - Sent::"+message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
