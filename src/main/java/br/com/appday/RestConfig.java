package br.com.appday;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import br.com.appday.product.endpoint.ProductEndPoint;

@Component
@Configuration
@ApplicationPath("/")
public class RestConfig extends ResourceConfig {

    public RestConfig() {
        register(ProductEndPoint.class);
    }

}
