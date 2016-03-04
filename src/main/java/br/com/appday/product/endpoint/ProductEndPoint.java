package br.com.appday.product.endpoint;

import br.com.appday.product.domain.Product;
import br.com.appday.product.service.ProductService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Component
@Path("/products")
@Consumes("application/json")
@Produces("application/json")
public class ProductEndPoint {

    Logger LOGGER = LoggerFactory.getLogger(ProductEndPoint.class);

    @Autowired
    private ProductService productService;

    @GET
    @JsonCreator
    public Response getAll() {

        LOGGER.debug("Start getAll()");

        Resources<Product> resources = new Resources<>(productService.findAll(), JaxRsLinkBuilder
                .linkTo(ProductEndPoint.class).withSelfRel());

        LOGGER.debug("Ended getAll()");

        return Response.ok(resources).build();
    }

    @POST
    public void create(Product product) {
        productService.save(product);
        productService.insertInRedisCache(product);

    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadImage(@PathParam("id") String id, @FormDataParam("file") InputStream fileInputStream,
                            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        productService.saveImage(id, fileInputStream, fileDetail.getType());
    }

}
