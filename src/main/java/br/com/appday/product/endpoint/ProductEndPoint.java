package br.com.appday.product.endpoint;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import br.com.appday.product.model.Product;
import jersey.repackaged.com.google.common.collect.Lists;

@Component
@Path("/products")
public class ProductEndPoint {

    @GET
    @Produces("application/json")
    public List<Product> getAll() {
        return Lists.newArrayList(new Product());
    }

}
