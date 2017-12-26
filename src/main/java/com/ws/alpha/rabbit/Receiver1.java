package com.ws.alpha.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author laowang
 */
@Component
@RabbitListener(queues = "danmu.queue")
public class Receiver1 {

    private Logger logger = LoggerFactory.getLogger(Receiver1.class);

    @RabbitHandler
    public String processMessage(String message) {
        logger.info(Thread.currentThread().getName() + "get the danmu.queue message");
        return message;
    }



}
