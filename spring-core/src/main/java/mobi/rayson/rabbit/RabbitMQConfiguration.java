package mobi.rayson.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-08-28
 *  Time: 下午2:41
 *  Description:
 **/
@Configuration
public class RabbitMQConfiguration {
    public static final String TEST_RABBIT_USER_QUEUE = "test_rabbit_user_queue";
    public static final String EXCHANGE = "test_rabbit_user_exchange";

    @Bean
    Queue productQueue() {
        return new Queue(TEST_RABBIT_USER_QUEUE, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TEST_RABBIT_USER_QUEUE);
    }
}
