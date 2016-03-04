package br.com.appday.product.domain.repository;

import br.com.appday.product.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
