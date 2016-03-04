package br.com.appday;

import br.com.appday.product.receivers.ProductReceiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "br.com.appday.product.domain.repository")
public class ApplicationConfiguration extends SpringBootServletInitializer {

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
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
											MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("products"));

		return container;
	}

    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationConfiguration.class);
	}

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfiguration.class, args);
    }
}
