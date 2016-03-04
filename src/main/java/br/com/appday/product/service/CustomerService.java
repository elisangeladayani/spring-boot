package br.com.appday.product.service;

import br.com.appday.product.domain.Customer;
import br.com.appday.product.domain.repository.CustomerRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CustomerService {

  @Autowired
  private CustomerRepository repository;

  public List<Customer> getAll() {
    return Lists.newArrayList(repository.findAll());
  }

  public void save(Customer customer) {
    customer.setCreatedAt(new Date());
    repository.save(customer);
  }

}
