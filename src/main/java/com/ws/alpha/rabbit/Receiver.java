package com.ws.alpha.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private Logger logger = LoggerFactory.getLogger(Receiver.class);

    @RabbitListener(queues = "danmu.queue")
    public String processMessage(String message) {
        logger.info(Thread.currentThread().getName() + "get the danmu.queue message");
        return message;
    }



}
