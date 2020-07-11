package org.hotelapp.billing.Controllers;

import org.hotelapp.billing.Services.RabbitMQService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Controller
@RestController
public class BillingController {
    @GetMapping("/")
    public String init() throws IOException, TimeoutException {
        new RabbitMQService().recieveMessage();
        return "Hello World";
    }

    @GetMapping("/trigger")
    public String triggerMQTask() throws IOException, TimeoutException {
        new RabbitMQService().publishMessage();
        return "Done triggering";
    }
}
