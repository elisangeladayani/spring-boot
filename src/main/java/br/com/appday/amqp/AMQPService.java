package br.com.appday.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by vbatista on 3/6/16.
 */
@Component
public class AMQPService {
    final static String queueName = "customerQueue";

    @Autowired
    RabbitTemplate rabbitTemplate;


    public void postMessage(Object o){
        rabbitTemplate.convertAndSend(queueName, o.toString());
    }
}
