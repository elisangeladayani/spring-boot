package br.com.appday.product.endpoint;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.appday.product.domain.Product;
import br.com.appday.product.service.ProductService;

@Component
@Path("/products")
@Consumes("application/json")
@Produces("application/json")
public class ProductEndPoint {

    @Autowired
    private ProductService productService;

    @GET
    public List<Product> getAll() {
        return productService.findAll();
    }

    @POST
    public void create(Product product) {
        productService.save(product);

    }

}
