package com.ws.alpha.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author laowang
 */
@Configuration
public class RabbitConfig {

    //声明队列
    @Bean
    public Queue queue() {
        return new Queue("danmu.queue", true);
    }

    @Bean
    public Queue queue1() {
        return new Queue("hello");
    }

    //声明交换机
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("danmuTopicExchange");
    }

    //绑定
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("danmu.#");
    }

}