package br.com.appday;

import br.com.appday.amqp.AMQPReceiver;
import br.com.appday.minio.MinioTemplate;
import br.com.appday.product.domain.Product;
import br.com.appday.product.receivers.ProductReceiver;
import io.minio.MinioClient;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "br.com.appday.product.domain.repository")
public class ApplicationConfiguration extends SpringBootServletInitializer {

    final static String queueName = "customerQueue";

    @Autowired
    MinioTemplate minioTemplate;

    @Bean
    StringRedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    ProductReceiver productReceiver() {
        return new ProductReceiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ProductReceiver productReceiver) {
        return new MessageListenerAdapter(productReceiver, "receiveMessage");
    }


    @Bean
    public Jackson2JsonRedisSerializer<Product> jsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Product.class);
    }

    @Bean
    RedisTemplate<String, Product> productCache(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Product> template = new RedisTemplate<String, Product>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(jsonRedisSerializer());
        return template;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("products"));

        return container;
    }

    @Bean
    MinioClient minioClient(Environment env) {
        return minioTemplate.getMinioClient();
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("dojo-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(queueName);
    }

    @Bean
    SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setQueueNames(queueName);
        messageListenerContainer.setMessageListener(messageListenerAdapter);
        return messageListenerContainer;
    }

    @Bean
    AMQPReceiver amqpReceiver() {
        return new AMQPReceiver();
    }

    @Bean
    org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter messageListenerAdapter(AMQPReceiver amqpReceiver) {
        return new org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter(amqpReceiver, "receiveMessage");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationConfiguration.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfiguration.class, args);
    }
}
