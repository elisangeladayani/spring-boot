package br.com.appday.product.endpoint;

import br.com.appday.product.domain.Product;
import br.com.appday.product.service.ProductService;
import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.jvnet.mimepull.MIMEPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.lang.reflect.Field;

@Component
@Path("/products")
@Consumes("application/json")
@Produces("application/json")
public class ProductEndPoint {

    Logger LOGGER = LoggerFactory.getLogger(ProductEndPoint.class);

    @Autowired
    private ProductService productService;

    @GET
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

