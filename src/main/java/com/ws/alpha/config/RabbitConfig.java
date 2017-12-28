package com.ws.alpha.config;

import com.ws.alpha.util.Constant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * @author laowang
 */
public class RabbitConfig {

    /**
     * 客户端发送到服务端的监听队列
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(Constant.QUEUE_NAME);
    }

    /**
     * 客户端直接发送消息到该exchange
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(Constant.EXCHANGE);
    }

    /**
     * 将exchange和queue进行绑定
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

}
