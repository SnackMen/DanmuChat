package com.ws.alpha.rabbit;

import com.ws.alpha.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author laowang
 */
@Component
public class RabbitResumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitResumer.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = Constant.QUEUE_NAME)
    public void process(String message) {
        //将消息转发到/wechat/message下面
        logger.info("rabbit server forward message to web server!");
        logger.info("message: {}", message);
        simpMessagingTemplate.convertAndSend("/wechat/message", message);
    }

}
