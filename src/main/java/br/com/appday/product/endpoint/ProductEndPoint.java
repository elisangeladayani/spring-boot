package br.com.appday.product.endpoint;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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

    @POST
    @Path("/{id}")
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    public void uploadImage(@Context HttpServletRequest httpRequest, @PathParam("id") String id) {

        try {
            productService.saveImage(id, httpRequest.getInputStream(), httpRequest.getContentType());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
