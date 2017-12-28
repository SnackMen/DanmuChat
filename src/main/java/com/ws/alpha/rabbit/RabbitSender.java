package com.ws.alpha.rabbit;

import com.ws.alpha.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author laowang
 */
@Component
public class RabbitSender {

    private static final Logger logger = LoggerFactory.getLogger(RabbitSender.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 消息发送
     * @param message
     */
    public void send(String message) {
        logger.info("client forward message to rabbit client: {}", message);
        this.rabbitTemplate.convertAndSend(Constant.EXCHANGE, "", message);
    }

}
