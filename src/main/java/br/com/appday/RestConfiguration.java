package br.com.appday;

import javax.ws.rs.ApplicationPath;

import br.com.appday.product.endpoint.CustomerEndPoint;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import br.com.appday.product.endpoint.ProductEndPoint;

@Configuration
@ApplicationPath("/")
public class RestConfiguration extends ResourceConfig {

  public RestConfiguration() {
    register(ProductEndPoint.class);
    register(CustomerEndPoint.class);
    register(MultiPartFeature.class);
  }

}
