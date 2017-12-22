package com.ws.alpha.rabbit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class Send implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Logger logger = LoggerFactory.getLogger(Send.class);

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if(b) {
            logger.info("rabbit message send success!");
        }else {
            logger.error("rabbit message send fail!");
        }

    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        logger.info(message.getMessageProperties().getCorrelationIdString());
    }

    /**
     * 发送消息，不需要实现任何接口，供外部调用
     * @param message
     */
    public void send(String message) {
        CorrelationData correlationDataId = new CorrelationData(UUID.randomUUID().toString());
        logger.info("begin send message:" + message);
        String response = rabbitTemplate.convertSendAndReceive("danmuTopicExchange",
                "danmu.danmu", message, correlationDataId).toString();
        logger.info("response message:" + response);
    }
}
