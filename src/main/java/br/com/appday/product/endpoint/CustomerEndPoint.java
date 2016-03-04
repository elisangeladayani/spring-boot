package br.com.appday.product.endpoint;

import br.com.appday.product.domain.Customer;
import br.com.appday.product.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.util.List;

@Component
@Path("/customers")
@Consumes("application/json")
@Produces("application/json")
public class CustomerEndPoint {

  @Autowired
  private CustomerService customerService;

  @GET
  public List<Customer> getAll() {
    return customerService.getAll();
  }

  @POST
  public void create(Customer customer) {
    customerService.save(customer);
  }

}
