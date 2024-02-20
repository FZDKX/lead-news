package com.fzdkx.schedule.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 发着呆看星
 * @create 2024/2/20
 */
@Configuration
public class MQConfiguration {
    @Bean
    public Queue simpleQueue(){
        // 创建普通队列，指定死信交换机
        return QueueBuilder.durable("simple.queue")  // 指定队列名称并持久化
                .deadLetterExchange("schedule.direct")   // 指定死信交换机
                .deadLetterRoutingKey("schedule")       // 指定死信routingKey
                .build();
    }

    @Bean
    public DirectExchange directExchange(){
        // 创建死信交换机
        return new DirectExchange("schedule.direct");
    }

    @Bean
    public Queue scheduleQueue() {
        // 创建死信队列
        return new Queue("schedule.queue");
    }

    @Bean
    public Binding dlBinding() {
        // 绑定死信交换机与死信队列
        return BindingBuilder.bind(scheduleQueue()).to(directExchange()).with("schedule");
    }

    // 标准队列
    @Bean
    public Queue normalQueue(){
        // 创建普通队列，指定死信交换机
        return QueueBuilder.durable("normal.queue")  // 指定队列名称并持久化
                .build();
    }
}
