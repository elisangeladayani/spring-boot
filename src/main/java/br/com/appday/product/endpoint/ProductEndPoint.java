package br.com.appday.product.endpoint;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
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
        List<Product> result = productService.findAll();
        for(Product product: result) {
            product.setName("-" + product.getName());
        }

        return result;
    }

    @POST
    public void create(Product product) {
        productService.save(product);

    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadImage(@PathParam("id") String id,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        productService.saveImage(id, fileInputStream, fileDetail.getType());
    }

}
