package br.com.appday.product.endpoint;

import br.com.appday.product.domain.Product;
import br.com.appday.product.service.ProductService;
import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.server.ManagedAsync;
import org.jvnet.mimepull.MIMEPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

@Component
@Path("/products")
@Consumes("application/json")
@Produces("application/json")
public class ProductEndPoint {

    Logger LOGGER = LoggerFactory.getLogger(ProductEndPoint.class);

    @Autowired
    private ProductService productService;

    @GET
    @Path("/async")
    @ManagedAsync
    /**
     * NOT HATEOAS
     */
    public void asyncGetAll(@Suspended final AsyncResponse asyncResponse) {
        LOGGER.debug("Start getAll() async");
        asyncResponse.resume(Response.ok(productService.findAll()).build());
        LOGGER.debug("Ended getAll() async");
    }

    @GET
    /**
     * HATEOAS
     */
    public Response syncGetAll() {
        return findAll("sync");
    }

    private Response findAll(String path) {

        LOGGER.debug("Start getAll() {}", path);
        Resources<Product> resources = new Resources<>(productService.findAll(), JaxRsLinkBuilder
                .linkTo(ProductEndPoint.class).withSelfRel());
        LOGGER.debug("Ended getAll() {}", path);

        return Response.ok(resources).build();
    }

    @POST
    public void create(Product product) {
        productService.save(product);
        productService.insertInRedisCache(product);

        String msgbody = "Olá, o produto " + product.getName() + " foi cadastrado com sucesso.";

        productService.sendMail("vsoares@gmail.com", "Produto cadastrado", msgbody);

    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadImage(@PathParam("id") String id, FormDataMultiPart form) {

        try {
            File tempFile = createTempFile(form, "file");
            productService.saveImage(id, tempFile.getAbsolutePath());
            LOGGER.debug("Ended uploadImage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File createTempFile(FormDataMultiPart form, String field) throws Exception {
        FormDataBodyPart filePart = form.getField(field);
        BodyPartEntity bodyPart = (BodyPartEntity) filePart.getEntity();

        MIMEPart mimePart = (MIMEPart) readFieldValue("mimePart", bodyPart);
        Object dataHead = readFieldValue("dataHead", mimePart);
        Object dataFile = readFieldValue("dataFile", dataHead);
        File tempFile = null;
        if (dataFile != null) {
            Object weakDataFile = readFieldValue("weak", dataFile);
            tempFile = (File) readFieldValue("file", weakDataFile);
        } else {
            tempFile = filePart.getValueAs(File.class);
        }

        return tempFile;
    }

    private static Object readFieldValue(String fieldName, Object o) throws Exception {
        Field field = o.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(o);
    }
}

