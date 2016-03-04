package br.com.appday.product.service;

import br.com.appday.product.domain.Product;
import com.google.api.client.util.Lists;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wvicente on 04/03/16.
 */
@Component
@Profile("teste")
public class TestProductService extends ProductService {

    @Override
    public List<Product> findAll() {
        List<Product> products = Lists.newArrayList();
        products.add(new Product("Com profile eh mais legal"));
        return products;
    }
    
}
