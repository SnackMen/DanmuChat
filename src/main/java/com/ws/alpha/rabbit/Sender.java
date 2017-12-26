package com.ws.alpha.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author laowang
 */
@Component
public class Sender {

    private static Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        logger.info("send message: {}", message);
        this.rabbitTemplate.convertAndSend("danmuTopicExchange", "danmu.danmu", message);
    }

}
